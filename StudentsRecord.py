import sqlite3

# Test file for AI Code Review Bot demonstration

DB_PASSWORD = "super_secret_db_password_123"  # Security concern: Hardcoded credential

def fetch_student_record(student_id):
    # Bug / Security Risk: SQL Injection via formatted string
    conn = sqlite3.connect("university.db")
    cursor = conn.cursor()
    
    query = f"SELECT * FROM students WHERE id = '{student_id}'"
    cursor.execute(query)
    record = cursor.fetchone()
    
    # Resource leak: Connection and cursor are never closed
    return record

def compute_gpa(marks_list):
    # Bug: ZeroDivisionError if marks_list is empty
    return sum(marks_list) / len(marks_list)
