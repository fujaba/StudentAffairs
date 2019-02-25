import TGroup from './tGroupModel/TGroup';
import TStudent from './tGroupModel/TStudent';
// import Yamler from "@fujaba/fulib-yaml-ts";
import { expect } from 'chai';
import 'mocha';

describe('TGroup model', () => {

    it('should have class TGroup and TStudent', () => {
      const tGroup = new TGroup();
      expect(tGroup).to.be.not.undefined;

      tGroup.name = "Martin";
      expect(tGroup.name).equals("Martin");

      const alice = new TStudent();
      expect(alice).to.be.not.undefined;
      alice.name = "Alice";
      alice.studentId = "m42";

      const carli = new TStudent();
      carli.name = "Carli";
      carli.studentId = "m23";

      tGroup.withStudents(alice, carli);
      expect(tGroup.students).contains(alice);
      expect(alice.tGroup).equals(tGroup);
      expect(carli.tGroup).equals(tGroup);

      alice.tGroup = null;

      expect(tGroup.students).not.contains(alice);


      // let text : string = JSON.stringify(tGroup);
      // console.log(text);
    });


  });