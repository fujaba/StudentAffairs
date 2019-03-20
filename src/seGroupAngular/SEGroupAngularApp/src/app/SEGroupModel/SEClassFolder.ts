import SEClassFolder from "./SEClassFolder";import SEClass from "./SEClass";

import SEGroup from "./SEGroup";

  export default class SEClassFolder  
{
    public name: string ;

    constructor() {
      this.name = '';
      this._group = null;
      this._subFolders = [];
      this._parent = null;
      this._classes = [];

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
          oldValue.withoutClassFolder(this);
        }
        this._group = value;
        if (value) {
          value.withClassFolder(this);
        }
      }
    }



    private _subFolders: SEClassFolder[];

    get subFolders(): SEClassFolder[] {
      return this._subFolders;
    }

    public withSubFolders(...value: any[]): SEClassFolder {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withSubFolders(item);
        } else if (item instanceof SEClassFolder) {
          if (!this._subFolders.includes(item)) {
            this._subFolders.push(item);
            (item as SEClassFolder).parent = this;
          }
        }
      }

      return this;
    }



    public withoutSubFolders(...value: any[]): SEClassFolder {
      if (this._subFolders === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutSubFolders(...item);
        } else if (item instanceof SEClassFolder) {
          if (this._subFolders.includes(item)) {
            this._subFolders.splice(this._subFolders.indexOf(item, 0), 1);
            (item as SEClassFolder).parent = null;
          }
        }
      }
      return this;
    }


    private _parent: SEClassFolder;

    get parent(): SEClassFolder {
      return this._parent;
    }

    set parent(value: SEClassFolder) {
      if (this._parent !== value) {
        const oldValue: SEClassFolder = this._parent;
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



    private _classes: SEClass[];

    get classes(): SEClass[] {
      return this._classes;
    }

    public withClasses(...value: any[]): SEClassFolder {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withClasses(item);
        } else if (item instanceof SEClass) {
          if (!this._classes.includes(item)) {
            this._classes.push(item);
            (item as SEClass).folder = this;
          }
        }
      }

      return this;
    }



    public withoutClasses(...value: any[]): SEClassFolder {
      if (this._classes === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutClasses(...item);
        } else if (item instanceof SEClass) {
          if (this._classes.includes(item)) {
            this._classes.splice(this._classes.indexOf(item, 0), 1);
            (item as SEClass).folder = null;
          }
        }
      }
      return this;
    }


    public removeYou() {
      this.group = null;
      this.withoutSubFolders(this._subFolders);
      this.parent = null;
      this.withoutClasses(this._classes);

    }


}