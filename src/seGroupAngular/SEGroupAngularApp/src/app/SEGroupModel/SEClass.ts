import Assignment from "./Assignment";

import Achievement from "./Achievement";

import SEGroup from "./SEGroup";import SEClassFolder from "./SEClassFolder";



  export default class SEClass  
{
    public topic: string ;

    public term: string ;

    constructor() {
      this.topic = '';
      this.term = '';
      this._group = null;
      this._currentGroup = null;
      this._folder = null;
      this._assignments = [];
      this._participations = [];

    }

    private _group: SEGroup;

    get group(): SEGroup {
      return this._group;
    }

    set group(value: SEGroup) {
      if (this._group !== value) {
        const oldValue: SEGroup = this._group;
        if (this._group) {
          this._group = null;
          oldValue.withoutClasses(this);
        }
        this._group = value;
        if (value) {
          value.withClasses(this);
        }
      }
    }



    private _assignments: Assignment[];

    get assignments(): Assignment[] {
      return this._assignments;
    }

    public withAssignments(...value: any[]): SEClass {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withAssignments(item);
        } else if (item instanceof Assignment) {
          if (!this._assignments.includes(item)) {
            this._assignments.push(item);
            (item as Assignment).seClass = this;
          }
        }
      }

      return this;
    }



    public withoutAssignments(...value: any[]): SEClass {
      if (this._assignments === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutAssignments(...item);
        } else if (item instanceof Assignment) {
          if (this._assignments.includes(item)) {
            this._assignments.splice(this._assignments.indexOf(item, 0), 1);
            (item as Assignment).seClass = null;
          }
        }
      }
      return this;
    }


    private _participations: Achievement[];

    get participations(): Achievement[] {
      return this._participations;
    }

    public withParticipations(...value: any[]): SEClass {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withParticipations(item);
        } else if (item instanceof Achievement) {
          if (!this._participations.includes(item)) {
            this._participations.push(item);
            (item as Achievement).seClass = this;
          }
        }
      }

      return this;
    }



    public withoutParticipations(...value: any[]): SEClass {
      if (this._participations === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutParticipations(...item);
        } else if (item instanceof Achievement) {
          if (this._participations.includes(item)) {
            this._participations.splice(this._participations.indexOf(item, 0), 1);
            (item as Achievement).seClass = null;
          }
        }
      }
      return this;
    }


    public removeYou() {
      this.group = null;
      this.currentGroup = null;
      this.folder = null;
      this.withoutAssignments(this._assignments);
      this.withoutParticipations(this._participations);

    }


    private _folder: SEClassFolder;

    get folder(): SEClassFolder {
      return this._folder;
    }

    set folder(value: SEClassFolder) {
      if (this._folder !== value) {
        const oldValue: SEClassFolder = this._folder;
        if (this._folder) {
          this._folder = null;
          oldValue.withoutClasses(this);
        }
        this._folder = value;
        if (value) {
          value.withClasses(this);
        }
      }
    }



    private _currentGroup: SEGroup;

    get currentGroup(): SEGroup {
      return this._currentGroup;
    }

    set currentGroup(value: SEGroup) {
      if (this._currentGroup !== value) {
        const oldValue: SEGroup = this._currentGroup;
        if (this._currentGroup) {
          this._currentGroup = null;
          oldValue.currentClass = null;
        }
        this._currentGroup = value;
        if (value) {
          value.currentClass = this;
        }
      }
    }



}