import Achievement from "./Achievement";
import Assignment from "./Assignment";

export default class Solution  
{
  public gitUrl: string ;
  public points: double ;
  private achievement: Achievement;
  private assignment: Assignment;

  constructor() {
      this.gitUrl = '';
      this.points = 0;
      this._achievement = null;
      this._assignment = null;

    }

    private _achievement: Achievement;

    get achievement(): Achievement {
      return this._achievement;
    }

    set achievement(value: Achievement) {
      if (this._achievement !== value) {
        const oldValue: Achievement = this._achievement;
        if (this._achievement) {
          this._achievement = null;
          oldValue.withoutSolutions(this);
        }
        this._achievement = value;
        if (value) {
          value.withSolutions(this);
        }
      }
    }



    private _assignment: Assignment;

    get assignment(): Assignment {
      return this._assignment;
    }

    set assignment(value: Assignment) {
      if (this._assignment !== value) {
        const oldValue: Assignment = this._assignment;
        if (this._assignment) {
          this._assignment = null;
          oldValue.withoutSolutions(this);
        }
        this._assignment = value;
        if (value) {
          value.withSolutions(this);
        }
      }
    }



    public removeYou() {
      this.achievement = null;
      this.assignment = null;

    }

}