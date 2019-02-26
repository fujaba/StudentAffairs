import TGroup from "./TGroup";

export default class TStudent  
{
  public studentId: string ;

  public name: string ;

  constructor() {
    this.studentId = '';
    this.name = '';
    this._tGroup = null;

  }

  private _tGroup: TGroup;

  get tGroup(): TGroup {
    return this._tGroup;
  }

  set tGroup(value: TGroup) {
    if (this._tGroup !== value) {
      const oldValue: TGroup = this._tGroup;
      if (this._tGroup) {
        this._tGroup = null;
        oldValue.withoutStudents(this);
      }
      this._tGroup = value;
      if (value) {
        value.withStudents(this);
      }
    }
  }



  public removeYou() {
    this.tGroup = null;

  }


}