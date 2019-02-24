import SEClass from "@/model/seClass";
import SEStudent from "@/model/seStudent";
import Solution from "@/model/solution";

export default class Achievement {
  private grade: string;
  private officeStatus: string;
  public seClass: SEClass;
  private _student: SEStudent;
  private solutions: Solution[];

  constructor() {
    this.grade = '';
    this.officeStatus = '';
    this.seClass = {} as SEClass;
    this._student = {} as SEStudent;
    this.solutions = [];
  }

  get student(): SEStudent {
    return this._student;
  } 

  set student(value: SEStudent) {
    if (!value ? this._student !== {} as SEStudent : value !== this._student) {
      this._student = value;
    }
  }

}