import SEClass from "@/model/seClass";
import SEStudent from "@/model/seStudent";

export default class SEGroup {
  public head: string;
  private readonly _classes: SEClass[];
  private readonly _students: SEStudent[];
  
  constructor() {
    this.head = '';
    this._classes = [];
    this._students = [];
  }

  
  get classes(): SEClass[] {
    return this._classes;
  }
  
  public withClasses(...value: any[]): SEGroup {
    if (!value) return this;
    
    for (const item of value) {
      if (!item) continue;
      if (item instanceof Array) {
        for (const i of item) {
          this.withClasses(...i);
        }
      } else if (item instanceof SEClass) {
        if (!this._classes.includes(item)) {
          this._classes.push(item);
          (item as SEClass).group = this; 
        }
      }
    }
      
    return this;
  }
  
  public withoutClasses(...value: any[]): SEGroup {
    if (this._classes === [] || !value) return this;
        
    for (const item of value) {
      if (!item) continue;
      if (item instanceof Array) {
        for( const i of item) {
          this.withoutClasses(...i);
        }
      } else if (item instanceof SEClass) {
        if (this._classes.includes(item)) {
          this._classes.splice(this._classes.indexOf(item, 0), 1);
          (item as SEClass).group = {} as SEGroup;
        }
      }
    }
    
    return this;
  }
  
  get students(): SEStudent[] {
    return this._students;
  }
  
  public withStudents(...value: any[]): SEGroup {
    if (!value) return this;

    for (const item of value) {
      if (!item) continue;
      if (item instanceof Array) {
        for (const i of item) {
          this.withStudents(...i);
        }
      } else if (item instanceof SEStudent) {
        if (!this._students.includes(item)) {
          this._students.push(item);
          (item as SEStudent).group = this;
        }
      }
    }
    
    return this;
  }

  public withoutStudents(...value: any[]): SEGroup {
    if (this._students === [] || !value) return this;
    
    for (const item of value) {
      if (!item) continue;
      if (item instanceof Array) {
        for( const i of item) {
          this.withoutStudents(...i);
        }
      } else if (item instanceof SEStudent) {
        if (this._students.includes(item)) {
          this._students.splice(this._students.indexOf(item, 0), 1);
          (item as SEStudent).group = {} as SEGroup;
        }
      }
    }

    return this;
  }
  
  public removeYou() {
    this.withoutClasses(Object.assign([], this._classes));
    this.withoutStudents(Object.assign([], this._students));
  }
  
  public getStudent(studentId: string): SEStudent | undefined {
    for (const s of this._students) {
      if (s.studentId === studentId) {
        return s;
      }
    }
    
    return undefined;
  }
  
  public getClass(topic: string, term: string): SEClass | undefined {
    let firstFit: any = undefined;
    
    for (const c of this._classes) {
      if (c.topic === topic && c.term === term) {
        return c;
      }
      if (term.lastIndexOf('-') >= 5
            && c.topic === topic 
            && c.term.localeCompare(term) < 0) {
        if (!firstFit) {
          firstFit = c;
        } else if (firstFit.term.localeCompare(c.term) < 0) {
          firstFit = c;
        }
      }
    }
    
    return firstFit;
  }
}