package uniks.accounting.view.studentOffice.subController;

import uniks.accounting.studentOffice.Course;
import uniks.accounting.studentOffice.StudyProgram;
import uniks.accounting.studentOffice.UniStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.modelView;

public class StudyProgramController implements SubController {

    private OfficeTreeItem view;
    private StudyProgram program;
    
    private OfficeTreeItem students;
    private OfficeTreeItem courses;
    
    public StudyProgramController(OfficeTreeItem view, StudyProgram program) {
        this.view = view;
        this.program = program;
    }
    
    public void init() {
        this.students = new OfficeTreeItem("Students");
        this.courses = new OfficeTreeItem("Courses");
        
        this.view.getChildren().add(this.students);
        this.view.getChildren().add(this.courses);
        this.view.setExpanded(true);
        
        modelView.put(this.students.getId(), this);
        modelView.put(this.courses.getId(), this);
        
        this.addModelListener();
    }

    private void addModelListener() {
        this.program.addPropertyChangeListener(StudyProgram.PROPERTY_subject, evt -> {
           if (evt.getNewValue() != null ) {
               this.view.setValue(evt.getNewValue() + "");
           }
        });
        
        this.program.addPropertyChangeListener(StudyProgram.PROPERTY_students, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                UniStudent student = (UniStudent) evt.getNewValue();
                OfficeTreeItem newStudent = new OfficeTreeItem(student.getName() + " - " + student.getStudentId());
                this.students.getChildren().add(newStudent);
            }
        });
        
        this.program.addPropertyChangeListener(StudyProgram.PROPERTY_courses, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                Course course = (Course) evt.getNewValue();
                OfficeTreeItem newCourse = new OfficeTreeItem(course.getTitle());
                
                CourseController con = new CourseController(newCourse, course);
                con.init();
                
                modelView.put(newCourse.getId(), con);
                
                this.courses.getChildren().add(newCourse);
            }
        });
    }
    
    @Override
    public Object getModel() {
        return this.program;
    }
}
