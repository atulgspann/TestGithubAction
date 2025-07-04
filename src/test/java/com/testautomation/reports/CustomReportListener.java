package com.testautomation.reports;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Custom TestNG report listener that generates HTML reports in a separate folder
 */
public class CustomReportListener implements IReporter {
    
    private static final String REPORT_DIR = "custom-reports";
    private final List<TestResult> testResults = new ArrayList<>();
    
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        try {
            // Create custom reports directory
            String customReportDir = outputDirectory + File.separator + REPORT_DIR;
            Files.createDirectories(Paths.get(customReportDir));
            
            // Process test results
            processTestResults(suites);
            
            // Generate HTML report
            generateHtmlReport(customReportDir);
            
            System.out.println("Custom HTML report generated at: " + customReportDir + File.separator + "index.html");
            
        } catch (Exception e) {
            System.err.println("Error generating custom report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void processTestResults(List<ISuite> suites) {
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> suiteResultsMap = suite.getResults();
            for (ISuiteResult result : suiteResultsMap.values()) {
                ITestContext context = result.getTestContext();
                
                // Process passed tests
                for (ITestResult passedTest : context.getPassedTests().getAllResults()) {
                    TestResult testResult = createTestResult(passedTest, "PASS");
                    testResults.add(testResult);
                }
                
                // Process failed tests
                for (ITestResult failedTest : context.getFailedTests().getAllResults()) {
                    TestResult testResult = createTestResult(failedTest, "FAIL");
                    testResults.add(testResult);
                }
                
                // Process skipped tests
                for (ITestResult skippedTest : context.getSkippedTests().getAllResults()) {
                    TestResult testResult = createTestResult(skippedTest, "SKIP");
                    testResults.add(testResult);
                }
            }
        }
    }
    
    private TestResult createTestResult(ITestResult result, String status) {
        TestResult testResult = new TestResult();
        testResult.setTestName(result.getName());
        testResult.setClassName(result.getTestClass().getName());
        testResult.setStatus(status);
        testResult.setDuration(result.getEndMillis() - result.getStartMillis());
        testResult.setStartTime(new Date(result.getStartMillis()));
        testResult.setEndTime(new Date(result.getEndMillis()));
        
        // Get test groups
        String[] groups = result.getMethod().getGroups();
        testResult.setGroups(Arrays.asList(groups));
        
        // Get test description
        String description = result.getMethod().getDescription();
        if (description != null && !description.isEmpty()) {
            testResult.setDescription(description);
        }
        
        // Get failure details for failed tests
        if (status.equals("FAIL") && result.getThrowable() != null) {
            testResult.setErrorMessage(result.getThrowable().getMessage());
            testResult.setStackTrace(getStackTrace(result.getThrowable()));
        }
        
        return testResult;
    }
    
    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
    
    private void generateHtmlReport(String reportDir) throws IOException {
        long passedCount = testResults.stream().filter(t -> "PASS".equals(t.getStatus())).count();
        long failedCount = testResults.stream().filter(t -> "FAIL".equals(t.getStatus())).count();
        long skippedCount = testResults.stream().filter(t -> "SKIP".equals(t.getStatus())).count();
        long totalExecuted = testResults.stream().filter(t -> !"SKIP".equals(t.getStatus())).count();
        double passPercentage = totalExecuted > 0 ? (double) passedCount / totalExecuted * 100 : 0;
        
        String htmlContent = generateHtmlContent(passedCount, failedCount, skippedCount, passPercentage);
        
        try (FileWriter fileWriter = new FileWriter(reportDir + File.separator + "index.html")) {
            fileWriter.write(htmlContent);
        }
    }
    
    private String generateHtmlContent(long passedCount, long failedCount, long skippedCount, double passPercentage) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>Test Execution Report</title>\n");
        html.append("    <style>\n");
        html.append(getCssStyles());
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <div class=\"header\">\n");
        html.append("            <h1>Test Execution Report</h1>\n");
        html.append("            <div class=\"subtitle\">Generated on ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("</div>\n");
        html.append("        </div>\n");
        
        // Summary section
        html.append("        <div class=\"summary\">\n");
        html.append("            <div class=\"summary-card total\">\n");
        html.append("                <h3>").append(testResults.size()).append("</h3>\n");
        html.append("                <p>Total Tests</p>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"summary-card passed\">\n");
        html.append("                <h3>").append(passedCount).append("</h3>\n");
        html.append("                <p>Passed</p>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"summary-card failed\">\n");
        html.append("                <h3>").append(failedCount).append("</h3>\n");
        html.append("                <p>Failed</p>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"summary-card skipped\">\n");
        html.append("                <h3>").append(skippedCount).append("</h3>\n");
        html.append("                <p>Skipped</p>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"summary-card\">\n");
        html.append("                <h3>").append(String.format("%.1f", passPercentage)).append("%</h3>\n");
        html.append("                <p>Pass Rate</p>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        
        // Test results section
        html.append("        <div class=\"content\">\n");
        html.append("            <div class=\"section\">\n");
        html.append("                <h2>Test Results</h2>\n");
        html.append("                <table class=\"test-table\">\n");
        html.append("                    <thead>\n");
        html.append("                        <tr>\n");
        html.append("                            <th>Test Name</th>\n");
        html.append("                            <th>Class</th>\n");
        html.append("                            <th>Status</th>\n");
        html.append("                            <th>Duration</th>\n");
        html.append("                            <th>Groups</th>\n");
        html.append("                        </tr>\n");
        html.append("                    </thead>\n");
        html.append("                    <tbody>\n");
        
        for (TestResult result : testResults) {
            html.append("                        <tr>\n");
            html.append("                            <td>").append(result.getTestName()).append("</td>\n");
            html.append("                            <td>").append(result.getClassName()).append("</td>\n");
            html.append("                            <td><span class=\"status ").append(result.getStatus().toLowerCase()).append("\">").append(result.getStatus()).append("</span></td>\n");
            html.append("                            <td class=\"duration\">").append(result.getFormattedDuration()).append("</td>\n");
            html.append("                            <td class=\"groups\">").append(result.getGroupsAsString()).append("</td>\n");
            html.append("                        </tr>\n");
            
            // Add error details for failed tests
            if ("FAIL".equals(result.getStatus()) && result.getErrorMessage() != null) {
                html.append("                        <tr>\n");
                html.append("                            <td colspan=\"5\">\n");
                html.append("                                <div class=\"error-details\">\n");
                html.append("                                    <div class=\"error-message\">").append(result.getErrorMessage()).append("</div>\n");
                if (result.getStackTrace() != null) {
                    html.append("                                    <div class=\"stack-trace\">").append(result.getStackTrace()).append("</div>\n");
                }
                html.append("                                </div>\n");
                html.append("                            </td>\n");
                html.append("                        </tr>\n");
            }
        }
        
        html.append("                    </tbody>\n");
        html.append("                </table>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        
        html.append("        <div class=\"footer\">\n");
        html.append("            <p>Generated by Custom TestNG Report Listener</p>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>");
        
        return html.toString();
    }
    
    private String getCssStyles() {
        return """
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 20px;
                background-color: #f5f5f5;
            }
            
            .container {
                max-width: 1200px;
                margin: 0 auto;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                overflow: hidden;
            }
            
            .header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 30px;
                text-align: center;
            }
            
            .header h1 {
                margin: 0;
                font-size: 2.5em;
                font-weight: 300;
            }
            
            .header .subtitle {
                margin-top: 10px;
                opacity: 0.9;
                font-size: 1.1em;
            }
            
            .summary {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 20px;
                padding: 30px;
                background-color: #f8f9fa;
            }
            
            .summary-card {
                background: white;
                padding: 20px;
                border-radius: 8px;
                text-align: center;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            
            .summary-card h3 {
                margin: 0 0 10px 0;
                font-size: 2em;
                font-weight: 300;
            }
            
            .summary-card.passed h3 { color: #28a745; }
            .summary-card.failed h3 { color: #dc3545; }
            .summary-card.skipped h3 { color: #ffc107; }
            .summary-card.total h3 { color: #007bff; }
            
            .summary-card p {
                margin: 0;
                color: #6c757d;
                font-size: 0.9em;
                text-transform: uppercase;
                letter-spacing: 1px;
            }
            
            .content {
                padding: 30px;
            }
            
            .section {
                margin-bottom: 40px;
            }
            
            .section h2 {
                color: #333;
                border-bottom: 2px solid #667eea;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }
            
            .test-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            
            .test-table th {
                background-color: #667eea;
                color: white;
                padding: 15px;
                text-align: left;
                font-weight: 500;
            }
            
            .test-table td {
                padding: 12px 15px;
                border-bottom: 1px solid #eee;
            }
            
            .test-table tr:hover {
                background-color: #f8f9fa;
            }
            
            .status {
                padding: 4px 8px;
                border-radius: 4px;
                font-size: 0.8em;
                font-weight: 500;
                text-transform: uppercase;
            }
            
            .status.pass {
                background-color: #d4edda;
                color: #155724;
            }
            
            .status.fail {
                background-color: #f8d7da;
                color: #721c24;
            }
            
            .status.skip {
                background-color: #fff3cd;
                color: #856404;
            }
            
            .groups {
                font-size: 0.8em;
                color: #6c757d;
            }
            
            .duration {
                font-family: monospace;
                color: #6c757d;
            }
            
            .error-details {
                background-color: #f8f9fa;
                border-left: 4px solid #dc3545;
                padding: 15px;
                margin-top: 10px;
                border-radius: 4px;
            }
            
            .error-message {
                color: #dc3545;
                font-weight: 500;
                margin-bottom: 10px;
            }
            
            .stack-trace {
                font-family: monospace;
                font-size: 0.9em;
                color: #6c757d;
                white-space: pre-wrap;
            }
            
            .footer {
                background-color: #f8f9fa;
                padding: 20px;
                text-align: center;
                color: #6c757d;
                border-top: 1px solid #dee2e6;
            }
            
            @media (max-width: 768px) {
                .summary {
                    grid-template-columns: repeat(2, 1fr);
                }
                
                .test-table {
                    font-size: 0.9em;
                }
                
                .test-table th,
                .test-table td {
                    padding: 8px;
                }
            }
            """;
    }
    
    // Inner class for data structure
    public static class TestResult {
        private String testName;
        private String className;
        private String status;
        private long duration;
        private Date startTime;
        private Date endTime;
        private List<String> groups;
        private String description;
        private String errorMessage;
        private String stackTrace;
        
        // Getters and setters
        public String getTestName() { return testName; }
        public void setTestName(String testName) { this.testName = testName; }
        
        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public long getDuration() { return duration; }
        public void setDuration(long duration) { this.duration = duration; }
        
        public Date getStartTime() { return startTime; }
        public void setStartTime(Date startTime) { this.startTime = startTime; }
        
        public Date getEndTime() { return endTime; }
        public void setEndTime(Date endTime) { this.endTime = endTime; }
        
        public List<String> getGroups() { return groups; }
        public void setGroups(List<String> groups) { this.groups = groups; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        
        public String getStackTrace() { return stackTrace; }
        public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
        
        public String getFormattedDuration() {
            return String.format("%.2f", duration / 1000.0) + "s";
        }
        
        public String getGroupsAsString() {
            return groups != null ? String.join(", ", groups) : "";
        }
    }
}
