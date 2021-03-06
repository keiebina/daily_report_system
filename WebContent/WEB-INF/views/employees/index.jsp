<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<c:import url="../layout/app.jsp">
<c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush }"></c:out>
            </div>
        </c:if>
        <h2>従業員 一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                    <th>フォロー</th>
                </tr>
                <c:forEach var="employeeView" items="${employeesViews }" varStatus="status">
                    <tr class="row${status.count % 2 }">
                        <td><c:out value="${employeeView.employee_code }"></c:out></td>
                        <td><c:out value="${employeeView.employee_name }"></c:out></td>
                        <td>
                            <c:choose>
                                <c:when test="${employeeView.employee_delete_flag == 1 }">
                                    (削除済み)
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/employees/show?id=${employeeView.employee_id }' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${employeeView.follow_flag == 1 }">
                                    <a href="<c:url value='/follows/update?follow_id=${employeeView.employee_id }' />">Following</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/follows/update?follow_id=${employeeView.employee_id }' />">Follow</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <script>

        </script>

        <div id="pagination">
            (全 ${employees_count }件)<br />
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page }">
                        <c:out value="${i }" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/index?page=${i }' />"><c:out value="${i }"></c:out></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/employees/new' />">新規従業員の登録</a></p>

    </c:param>
</c:import>