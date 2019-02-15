import SEGroup from "@/model/seGroup";
import Achievement from "@/model/achievement";
import SEClass from "@/model/seClass";

export default class SEStudent {
  private _studentId: string;
  private _teachingAssistantFor: string;
  private _group: SEGroup;
  private readonly _achievements: Achievement[];
    
  constructor() {
    this._studentId = '';
    this._teachingAssistantFor = '';
    this._group = {} as SEGroup;
    this._achievements = [];
  }
  
  get studentId(): string {
    return this._studentId;
  }
  
  set studentId(value: string) {
    if (!value ? this._studentId !== '' : value !== this._studentId) {
      this._studentId = value;
    }
  }
  
  get teachingAssistantFor(): string {
    return this._teachingAssistantFor;
  } 
  
  set teachingAssistantFor(value: string) {
    if (!value ? this._teachingAssistantFor !== '' : value !== this._teachingAssistantFor) {
      this._teachingAssistantFor = value;
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
        oldValue.withoutStudents(this);
      }
      this._group = value;
      if (value) {
        value.withStudents(this);
      }
    }
  }
  
  get achievements(): Achievement[] {
    return this._achievements;
  }

  public withAchievements(...value: any[]): SEStudent {
    if (!value) return this;

    for (const item of value) {
      if (!item) continue;
      if (item instanceof Array) {
        for (const i of item) {
          this.withAchievements(...i);
        }
      } else if (item instanceof Achievement) {
      if (!this._achievements.includes(item)) {
        this._achievements.push(item);
          (item as Achievement).student = this;
        }
      }
    }
    
    return this;
  }

  public withoutAchievements(...value: any[]): SEStudent {
    if (this._achievements === [] || !value) return this;
  
    for (const item of value) {
      if (!item) continue;
        if (item instanceof Array) {
        for( const i of item) {
          this.withoutAchievements(...i);
        }
      } else if (item instanceof Achievement) {
        if (this._achievements.includes(item)) {
          this._achievements.splice(this._achievements.indexOf(item, 0), 1);
          (item as Achievement).student = {} as SEStudent;
        }
      }
    }
    
    return this;
  }
  
  public removeYou() {
    this._group = {} as SEGroup;
    this.withoutAchievements(Object.assign([], this._achievements));
  }
  
  public getAchievements(seClass: SEClass): Achievement | undefined {
    for (const a of this._achievements) {
      if (a.seClass === seClass) return a;
    }
    
    return undefined;
  }
}