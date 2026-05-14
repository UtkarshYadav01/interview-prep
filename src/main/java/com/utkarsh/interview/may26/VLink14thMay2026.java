package com.utkarsh.interview.may26;

/**
 * VLink Interview – 14th May 2026
 */
public class VLink14thMay2026 {

    public static void main(String[] args) {
        System.out.println("VLink Interview – 14th May 2026");
    }
}

/*
 ═══════════════════════════════════════════════════════════════════
  SQL QUESTIONS
 ═══════════════════════════════════════════════════════════════════

Tables : Employees
EmployeeID , Name and DepartmentID
Tables : Departments
DepartmentID and DepartmentName

Write an SQL query to display the name of each employee, their department name,
and also include employees who do not belong to any department.
Order the results by employee name.

 YOUR ATTEMPT (wrong):
 ─────────────────────
    SELECT e.name, d.DepartmentName
    FROM employee AS e AND Department AS d        ❌ AND is not valid SQL here
    LEFT JOIN e.DepartmentID = e.DepartmentID     ❌ JOIN needs a table, not a column
    ORDER BY name

 CORRECT SOLUTION:
 ─────────────────
    SELECT
        e.Name AS EmployeeName,
        d.DepartmentName
    FROM Employees e
    LEFT JOIN Departments d
        ON e.DepartmentID = d.DepartmentID
    ORDER BY e.Name;

 WHY LEFT JOIN?
    LEFT JOIN returns ALL rows from the LEFT table (Employees) + matched rows
    from the right table (Departments).  If an employee has no department
    (DepartmentID IS NULL or no matching row), d.DepartmentName will be NULL
    in the result — but the employee still appears.
    INNER JOIN would silently drop those employees.

*/