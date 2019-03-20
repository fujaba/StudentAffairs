import SolutionFolder from "./SolutionFolder";import Assignment from "./Assignment";

import Solution from "./Solution";

  export default class SolutionFolder  
{
    public name: string ;

    constructor() {
      this.name = '';
      this._solutions = [];
      this._subFolders = [];
      this._parent = null;
      this._assignment = null;

    }

    private _solutions: Solution[];

    get solutions(): Solution[] {
      return this._solutions;
    }

    public withSolutions(...value: any[]): SolutionFolder {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withSolutions(item);
        } else if (item instanceof Solution) {
          if (!this._solutions.includes(item)) {
            this._solutions.push(item);
            (item as Solution).folder = this;
          }
        }
      }

      return this;
    }



    public withoutSolutions(...value: any[]): SolutionFolder {
      if (this._solutions === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutSolutions(...item);
        } else if (item instanceof Solution) {
          if (this._solutions.includes(item)) {
            this._solutions.splice(this._solutions.indexOf(item, 0), 1);
            (item as Solution).folder = null;
          }
        }
      }
      return this;
    }


    private _subFolders: SolutionFolder[];

    get subFolders(): SolutionFolder[] {
      return this._subFolders;
    }

    public withSubFolders(...value: any[]): SolutionFolder {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withSubFolders(item);
        } else if (item instanceof SolutionFolder) {
          if (!this._subFolders.includes(item)) {
            this._subFolders.push(item);
            (item as SolutionFolder).parent = this;
          }
        }
      }

      return this;
    }



    public withoutSubFolders(...value: any[]): SolutionFolder {
      if (this._subFolders === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutSubFolders(...item);
        } else if (item instanceof SolutionFolder) {
          if (this._subFolders.includes(item)) {
            this._subFolders.splice(this._subFolders.indexOf(item, 0), 1);
            (item as SolutionFolder).parent = null;
          }
        }
      }
      return this;
    }


    private _parent: SolutionFolder;

    get parent(): SolutionFolder {
      return this._parent;
    }

    set parent(value: SolutionFolder) {
      if (this._parent !== value) {
        const oldValue: SolutionFolder = this._parent;
        if (this._parent) {
          this._parent = null;
          oldValue.withoutSubFolders(this);
        }
        this._parent = value;
        if (value) {
          value.withSubFolders(this);
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
          oldValue.solutionFolder = null;
        }
        this._assignment = value;
        if (value) {
          value.solutionFolder = this;
        }
      }
    }



    public removeYou() {
      this.withoutSolutions(this._solutions);
      this.withoutSubFolders(this._subFolders);
      this.parent = null;
      this.assignment = null;

    }


}