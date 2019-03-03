import SEGroup from "./SEGroup";
import Achievement from "./Achievement";
import SEClass from "@/model/seClass";

export default class SEStudent  
{
  public studentId: string ;
  private _teachingAssistantFor: string;
  private _group: SEGroup;
  private _achievements: Achievement[];
    
  constructor() {
      this.studentId = '';
      this.teachingAssistantFor = '';
      this._group = null;
      this._achievements = [];

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
        if (this._group) {
          this._group = null;
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
          this.withAchievements(item);
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
          this.withoutAchievements(...item);
        } else if (item instanceof Achievement) {
          if (this._achievements.includes(item)) {
            this._achievements.splice(this._achievements.indexOf(item, 0), 1);
            (item as Achievement).student = null;
          }
        }
      }
      return this;
    }
  
  public removeYou() {
      this.group = null;
      this.withoutAchievements(this._achievements);

    }
  
  public getAchievements(seClass: SEClass): Achievement | undefined {
    for (const a of this._achievements) {
      if (a.seClass === seClass) return a;
    }
    
    return undefined;
  }
    public teachingAssistantFor: string ;

}