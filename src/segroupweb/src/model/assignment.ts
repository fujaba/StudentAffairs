import SEClass from "@/model/seClass";
import Solution from "@/model/solution";

export default class Assignment {
  private task: string;
  private points: string;
  private seClass: SEClass;
  private solutions: Solution[];
  
  constructor() {
    this.task = '';
    this.points = '';
    this.seClass = {} as SEClass;
    this.solutions = [];
  }
}