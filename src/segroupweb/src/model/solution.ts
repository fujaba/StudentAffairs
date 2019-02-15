import Achievement from "@/model/achievement";
import Assignment from "@/model/assignment";

export default class Solution {
  private gitUrl: string;
  private points: string;
  private achievement: Achievement;
  private assignment: Assignment;

  constructor() {
    this.gitUrl = '';
    this.points = '';
    this.achievement = {} as Achievement;
    this.assignment = {} as Assignment;
  }

}