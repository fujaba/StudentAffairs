import Assignment from "./Assignment";

import Achievement from "./Achievement";import SolutionFolder from "./SolutionFolder";



  export default class Solution  
{
    public gitUrl: string ;

    public points: number ;

    constructor() {
      this.gitUrl = '';
      this.points = 0;
      this._achievement = null;
      this._assignment = null;
      this._folder = null;

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
      this.folder = null;

    }


    private _folder: SolutionFolder;

    get folder(): SolutionFolder {
      return this._folder;
    }

    set folder(value: SolutionFolder) {
      if (this._folder !== value) {
        const oldValue: SolutionFolder = this._folder;
        if (this._folder) {
          this._folder = null;
          oldValue.withoutSolutions(this);
        }
        this._folder = value;
        if (value) {
          value.withSolutions(this);
        }
      }
    }



}