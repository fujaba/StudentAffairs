import SEGroup from "@/model/seGroup";
import Achievement from "@/model/achievement";
import Assignment from "@/model/assignment";

export default class SEClass {
  private _topic: string;
  private _term: string;
  private _group: SEGroup;
  private readonly _assignments: Assignment[];
  private readonly _participations: Achievement[];
  
  constructor() {
    this._topic = '';
    this._term = '';
    this._group = {} as SEGroup;
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
      if (this._group !== {} as SEGroup) {
        this._group = {} as SEGroup;
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
  public withAssignments
}