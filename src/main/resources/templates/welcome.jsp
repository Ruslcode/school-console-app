
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head><title>university-cms</title></head>
<body>
<h1>Home Page. You are logged by <span th:text="${accountType}"></span></h1>
<h3>Welcome to <span th:text="${appName}"></span></h3>
<h3>happy to see you again <span th:text="${loginName}"></span></h3>
<p>Show schedule;</p>
<p>Create new student;</p>
<p>Add student to the course;</p>
<p>show classmates;</p>
</body>
</html>