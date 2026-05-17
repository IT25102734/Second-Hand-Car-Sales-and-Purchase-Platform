package utils;

import models.PurchaseRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestService {
    private static final String REQUEST_FILE = "requests.txt";

    // CREATE
    public boolean addRequest(PurchaseRequest request) throws IOException {
        List<String> lines = FileUtil.readFromFile(REQUEST_FILE);
        int newId = lines.size() + 1;
        request.setId(newId);
        FileUtil.writeToFile(REQUEST_FILE, request.toFileString(), true);
        return true;
    }

    // READ all requests
    public List<PurchaseRequest> getAllRequests() throws IOException {
        List<PurchaseRequest> requests = new ArrayList<>();
        List<String> lines = FileUtil.readFromFile(REQUEST_FILE);
        for (String line : lines) {
            PurchaseRequest req = PurchaseRequest.fromFileString(line);
            if (req != null) requests.add(req);
        }
        return requests;
    }

    // READ requests by seller ID
    public List<PurchaseRequest> getRequestsBySeller(int sellerId) throws IOException {
        return getAllRequests().stream()
                .filter(r -> r.getSellerId() == sellerId)
                .collect(Collectors.toList());
    }

    // READ requests by buyer ID
    public List<PurchaseRequest> getRequestsByBuyer(int buyerId) throws IOException {
        return getAllRequests().stream()
                .filter(r -> r.getBuyerId() == buyerId)
                .collect(Collectors.toList());
    }

    // READ request by ID
    public PurchaseRequest getRequestById(int id) throws IOException {
        return getAllRequests().stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // UPDATE request status
    public boolean updateRequestStatus(int requestId, String newStatus) throws IOException {
        List<String> lines = FileUtil.readFromFile(REQUEST_FILE);
        List<String> newLines = new ArrayList<>();
        boolean updated = false;
        for (String line : lines) {
            PurchaseRequest req = PurchaseRequest.fromFileString(line);
            if (req != null && req.getId() == requestId) {
                req.setStatus(newStatus);
                newLines.add(req.toFileString());
                updated = true;
            } else {
                newLines.add(line);
            }
        }
        if (updated) {
            FileUtil.deleteFile(REQUEST_FILE);
            for (String line : newLines) {
                FileUtil.writeToFile(REQUEST_FILE, line, true);
            }
        }
        return updated;
    }
}