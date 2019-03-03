import Achievement from "./Achievement";

import SEGroup from "./SEGroup";

  export default class SEStudent  
{
    public studentId: string ;

    public teachingAssistantFor: string ;

    constructor() {
      this.studentId = '';
      this.teachingAssistantFor = '';
      this._group = null;
      this._achievements = [];

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
          oldValue.withoutStudents(this);
        }
        this._group = value;
        if (value) {
          value.withStudents(this);
        }
      }
    }



    private _achievements: Achievement[];

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


}