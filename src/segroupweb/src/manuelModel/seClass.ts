import SEGroup from "./SEGroup";
import Achievement from "./Achievement";
import Assignment from "./Assignment";

export default class SEClass  
{
  private _topic: string;
  private _term: string;
  private _group: SEGroup;
  private _assignments: Assignment[];
  private _participations: Achievement[];
  
  constructor() {
      this.topic = '';
      this.term = '';
      this._group = null;
      this._assignments = [];
      this._participations = [];

    }
  
  get topic(): string {
    return this._topic;
  }
  
  set topic(value: string) {
    if (!value ? this._topic !== '' : value !== this._topic) {
      this._topic = value;
    }
  }
  
  get term(): string {
    return this._term;
  }
  
  set term(value: string) {
    if (!value ? this._term !== '' : value !== this._term) {
      this._term = value;
    }
  }
  
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
  
  get assignments(): Assignment[] {
      return this._assignments;
    }
  
  // HIer gehts weiter
  // public withAssignments
    public topic: string ;

    public term: string ;

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
      this.withoutAssignments(this._assignments);
      this.withoutParticipations(this._participations);

    }

}