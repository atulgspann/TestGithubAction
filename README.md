# TestNG Maven Project with Groups

This project demonstrates automated testing with TestNG, Maven, and Selenium WebDriver. It includes test groups (smoke and regression) and Maven profiles for different test execution scenarios.

## Project Structure

```
├── pom.xml                          # Maven configuration with profiles
├── testng.xml                       # Default TestNG suite (all tests)
├── testng-smoke.xml                 # Smoke test suite
├── testng-regression.xml            # Regression test suite
├── testng-all.xml                   # All test groups suite
└── src/
    └── test/
        └── java/
            └── com/
                └── testautomation/
                    ├── tests
                    │   ├── CalculatorTest.java # Unit tests
                    │   ├── StringTest.java     # String manipulation tests
                    │   └── ArrayTest.java      # Array manipulation tests
                    └── reports/
                        └── CustomReportListener.java # Custom HTML report generator
```

## Test Groups

### Smoke Tests
- **LoginTest**: `testSuccessfulLogin()` - Tests successful login functionality
- **CalculatorTest**: `testAddition()`, `testSubtraction()` - Basic arithmetic operations
- **StringTest**: `testStringConcatenation()`, `testStringLength()` - Basic string operations
- **ArrayTest**: `testArrayCreationAndAccess()`, `testArraySorting()` - Basic array operations

### Regression Tests
- **LoginTest**: `testFailedLoginWithInvalidCredentials()`, `testLoginFormValidation()` - Login error scenarios
- **CalculatorTest**: `testMultiplication()`, `testDivision()`, `testDivisionByZero()`, `testNegativeNumbers()` - Advanced arithmetic
- **StringTest**: `testStringToUpperCase()`, `testStringToLowerCase()`, `testStringContains()`, `testStringTrim()` - String manipulation
- **ArrayTest**: `testArraySearch()`, `testArrayCopy()`, `testArrayFill()`, `testArrayEquality()` - Array operations

## Running Tests

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser (for Selenium tests)

### Maven Commands

#### Run all tests (default)
```bash
mvn test
```

#### Run smoke tests only
```bash
mvn test -Psmoke
```

#### Run regression tests only
```bash
mvn test -Pregression
```

#### Run all test groups (smoke + regression)
```bash
mvn test -Pall
```

#### Run specific test group using TestNG groups parameter
```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
mvn test -Dgroups=smoke,regression
```

#### Run specific test class
```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=CalculatorTest
```

#### Run specific test method
```bash
mvn test -Dtest=LoginTest#testSuccessfulLogin
```

### TestNG XML Files

You can also run tests using specific TestNG XML files:

```bash
# Run smoke tests
mvn test -DsuiteXmlFile=testng-smoke.xml

# Run regression tests
mvn test -DsuiteXmlFile=testng-regression.xml

# Run all test groups
mvn test -DsuiteXmlFile=testng-all.xml
```

## Maven Profiles

The project includes three Maven profiles:

1. **smoke** - Runs only smoke tests using `testng-smoke.xml`
2. **regression** - Runs only regression tests using `testng-regression.xml`
3. **all** - Runs all test groups using `testng-all.xml`

## Custom HTML Reports

This project includes a custom HTML report generator that creates beautiful, detailed reports in a separate folder. The custom report includes:

### Features
- **Modern UI Design** - Clean, responsive design with gradient headers
- **Summary Dashboard** - Visual cards showing test statistics
- **Detailed Test Results** - Complete test execution details
- **Error Details** - Stack traces and error messages for failed tests
- **Test Groups** - Information about test groups (smoke/regression)
- **Execution Times** - Duration for each test
- **Mobile Responsive** - Works on desktop and mobile devices

### Report Location
After test execution, the custom HTML report is generated at:
```
target/custom-reports/index.html
```

### Report Contents
- **Test Summary**: Total tests, passed, failed, skipped, and pass percentage
- **Test Details**: Test name, class, status, duration, and groups
- **Error Information**: Detailed error messages and stack traces for failed tests
- **Execution Statistics**: Timing information and test categorization

### Report Styling
The report uses modern CSS with:
- Gradient backgrounds
- Card-based layout
- Color-coded status indicators
- Hover effects
- Responsive design
- Professional typography

## Dependencies

- **TestNG 7.7.1** - Testing framework
- **Selenium WebDriver 4.10.0** - Web automation
- **WebDriverManager 5.3.2** - Automatic driver management
- **AssertJ 3.24.2** - Fluent assertions

## Test Execution Examples

### Quick Smoke Test Run
```bash
mvn clean test -Psmoke
```

### Full Regression Suite
```bash
mvn clean test -Pregression
```

### Complete Test Suite
```bash
mvn clean test -Pall
```

### Parallel Test Execution
```bash
mvn test -Pall -Dparallel=methods -DthreadCount=4
```

## Test Reports

### Default TestNG Reports
After test execution, TestNG generates HTML reports in the `target/surefire-reports` directory. You can open `index.html` to view detailed test results.

### Custom HTML Reports
The custom report generator creates enhanced HTML reports in `target/custom-reports/index.html` with:
- Better visual design
- Enhanced test details
- Error information
- Test group information
- Execution statistics

## Notes

- The LoginTest uses the demo site "https://the-internet.herokuapp.com/login" for web automation
- CalculatorTest, StringTest, and ArrayTest are unit tests that don't require external dependencies
- All tests include proper assertions and error messages
- The project follows Maven standard directory structure
- Custom reports are automatically generated for all test executions
- The custom report listener is configured in all Maven profiles
- The custom report generator uses pure Java without external template dependencies 
