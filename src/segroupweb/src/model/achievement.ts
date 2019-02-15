import SEClass from "@/model/seClass";
import SEStudent from "@/model/seStudent";
import Solution from "@/model/solution";

export default class Achievement {
  private grade: string;
  private officeStatus: string;
  private seClass: SEClass;
  private student: SEStudent;
  private solutions: Solution[];

  constructor() {
    this.grade = '';
    this.officeStatus = '';
    this.seClass = {} as SEClass;
    this.student = {} as SEStudent;
    this.solutions = [];
  }
}