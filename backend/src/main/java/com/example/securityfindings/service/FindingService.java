package com.example.securityfindings.service;

import com.example.securityfindings.dto.FindingCommentDto;
import com.example.securityfindings.dto.FindingCommentRequest;
import com.example.securityfindings.dto.FindingDto;
import com.example.securityfindings.dto.FindingHistoryDto;
import com.example.securityfindings.dto.FindingHistoryRequest;
import com.example.securityfindings.dto.FindingRequest;
import com.example.securityfindings.dto.EvidenceDto;
import com.example.securityfindings.entity.Evidence;
import com.example.securityfindings.entity.Finding;
import com.example.securityfindings.entity.FindingComment;
import com.example.securityfindings.entity.FindingHistory;
import com.example.securityfindings.entity.FindingStatus;
import com.example.securityfindings.entity.Project;
import com.example.securityfindings.entity.Risk;
import com.example.securityfindings.mapper.EvidenceMapper;
import com.example.securityfindings.mapper.FindingCommentMapper;
import com.example.securityfindings.mapper.FindingHistoryMapper;
import com.example.securityfindings.mapper.FindingMapper;
import com.example.securityfindings.repository.EvidenceRepository;
import com.example.securityfindings.repository.FindingCommentRepository;
import com.example.securityfindings.repository.FindingHistoryRepository;
import com.example.securityfindings.repository.FindingRepository;
import com.example.securityfindings.repository.ProjectRepository;
import com.example.securityfindings.repository.RiskRepository;
import com.example.securityfindings.util.ResourceNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FindingService {
    private final FindingRepository findingRepository;
    private final ProjectRepository projectRepository;
    private final RiskRepository riskRepository;
    private final FindingMapper findingMapper;
    private final FindingCommentRepository commentRepository;
    private final FindingHistoryRepository historyRepository;
    private final EvidenceRepository evidenceRepository;
    private final FindingCommentMapper commentMapper;
    private final FindingHistoryMapper historyMapper;
    private final EvidenceMapper evidenceMapper;
    private final EvidenceStorageService evidenceStorageService;

    public FindingService(
            FindingRepository findingRepository,
            ProjectRepository projectRepository,
            RiskRepository riskRepository,
            FindingMapper findingMapper,
            FindingCommentRepository commentRepository,
            FindingHistoryRepository historyRepository,
            EvidenceRepository evidenceRepository,
            FindingCommentMapper commentMapper,
            FindingHistoryMapper historyMapper,
            EvidenceMapper evidenceMapper,
            EvidenceStorageService evidenceStorageService
    ) {
        this.findingRepository = findingRepository;
        this.projectRepository = projectRepository;
        this.riskRepository = riskRepository;
        this.findingMapper = findingMapper;
        this.commentRepository = commentRepository;
        this.historyRepository = historyRepository;
        this.evidenceRepository = evidenceRepository;
        this.commentMapper = commentMapper;
        this.historyMapper = historyMapper;
        this.evidenceMapper = evidenceMapper;
        this.evidenceStorageService = evidenceStorageService;
    }

    public Page<FindingDto> search(UUID projectId, FindingStatus status, Pageable pageable) {
        return findingRepository.search(projectId, status, pageable).map(findingMapper::toDto);
    }

    public FindingDto getById(UUID id) {
        return findingMapper.toDto(findById(id));
    }

    @Transactional
    public FindingDto create(FindingRequest request) {
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + request.projectId()));
        Risk risk = riskRepository.findById(request.riskId())
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found: " + request.riskId()));

        Finding finding = new Finding();
        finding.setProject(project);
        finding.setRisk(risk);
        finding.setTitle(request.title());
        finding.setDescription(request.description());
        finding.setSeverity(request.severity());
        finding.setLikelihood(request.likelihood());
        finding.setImpact(request.impact());
        finding.setRiskLevel(request.riskLevel());
        finding.setStatus(request.status());
        finding.setOwner(request.owner());
        finding.setDueDate(request.dueDate());
        finding.setCreatedAt(Instant.now());
        if (request.controlTags() != null) {
            finding.setControlTags(request.controlTags());
        }

        Finding saved = findingRepository.save(finding);
        recordHistory(saved, request.owner(), null, request.status(), "CREATED", "Finding created");
        return findingMapper.toDto(saved);
    }

    @Transactional
    public FindingDto update(UUID id, FindingRequest request) {
        Finding finding = findById(id);
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + request.projectId()));
        Risk risk = riskRepository.findById(request.riskId())
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found: " + request.riskId()));

        FindingStatus previousStatus = finding.getStatus();
        finding.setProject(project);
        finding.setRisk(risk);
        finding.setTitle(request.title());
        finding.setDescription(request.description());
        finding.setSeverity(request.severity());
        finding.setLikelihood(request.likelihood());
        finding.setImpact(request.impact());
        finding.setRiskLevel(request.riskLevel());
        finding.setStatus(request.status());
        finding.setOwner(request.owner());
        finding.setDueDate(request.dueDate());
        finding.setControlTags(request.controlTags() == null ? finding.getControlTags() : request.controlTags());

        Finding saved = findingRepository.save(finding);
        if (previousStatus != request.status()) {
            recordHistory(saved, request.owner(), previousStatus, request.status(), "STATUS_UPDATED", "Status updated");
        }
        return findingMapper.toDto(saved);
    }

    @Transactional
    public void delete(UUID id) {
        if (!findingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Finding not found: " + id);
        }
        findingRepository.deleteById(id);
    }

    public List<FindingCommentDto> listComments(UUID findingId) {
        return commentRepository.findByFindingId(findingId).stream().map(commentMapper::toDto).toList();
    }

    @Transactional
    public FindingCommentDto addComment(UUID findingId, FindingCommentRequest request) {
        Finding finding = findById(findingId);
        FindingComment comment = new FindingComment();
        comment.setFinding(finding);
        comment.setAuthor(request.author());
        comment.setComment(request.comment());
        comment.setCreatedAt(Instant.now());
        FindingComment saved = commentRepository.save(comment);
        recordHistory(finding, request.author(), finding.getStatus(), finding.getStatus(), "COMMENT_ADDED", "Comment added");
        return commentMapper.toDto(saved);
    }

    public List<FindingHistoryDto> listHistory(UUID findingId) {
        return historyRepository.findByFindingId(findingId).stream().map(historyMapper::toDto).toList();
    }

    @Transactional
    public FindingHistoryDto addHistory(UUID findingId, FindingHistoryRequest request) {
        Finding finding = findById(findingId);
        FindingHistory history = new FindingHistory();
        history.setFinding(finding);
        history.setActor(request.actor());
        history.setFromStatus(request.fromStatus());
        history.setToStatus(request.toStatus());
        history.setAction(request.action());
        history.setDetails(request.details());
        history.setCreatedAt(Instant.now());
        FindingHistory saved = historyRepository.save(history);
        if (request.toStatus() != null) {
            finding.setStatus(request.toStatus());
            findingRepository.save(finding);
        }
        return historyMapper.toDto(saved);
    }

    public List<EvidenceDto> listEvidence(UUID findingId) {
        return evidenceRepository.findByFindingId(findingId).stream().map(evidenceMapper::toDto).toList();
    }

    @Transactional
    public EvidenceDto addEvidence(UUID findingId, MultipartFile file, String url) {
        Finding finding = findById(findingId);
        Evidence evidence = new Evidence();
        evidence.setFinding(finding);
        evidence.setFilename(file.getOriginalFilename());
        evidence.setContentType(file.getContentType());
        evidence.setSize(file.getSize());
        evidence.setStoragePath(evidenceStorageService.store(findingId, file));
        evidence.setUrl(url);
        evidence.setCreatedAt(Instant.now());
        Evidence saved = evidenceRepository.save(evidence);
        recordHistory(finding, finding.getOwner(), finding.getStatus(), finding.getStatus(), "EVIDENCE_ADDED", "Evidence uploaded");
        return evidenceMapper.toDto(saved);
    }

    private Finding findById(UUID id) {
        return findingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Finding not found: " + id));
    }

    private void recordHistory(Finding finding, String actor, FindingStatus fromStatus, FindingStatus toStatus, String action, String details) {
        FindingHistory history = new FindingHistory();
        history.setFinding(finding);
        history.setActor(actor);
        history.setFromStatus(fromStatus);
        history.setToStatus(toStatus);
        history.setAction(action);
        history.setDetails(details);
        history.setCreatedAt(Instant.now());
        historyRepository.save(history);
    }
}
