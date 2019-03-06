
import TGroup from './tGroupModel/TGroup';
import TStudent from './tGroupModel/TStudent';

import { Yamler, YamlIdMap } from '@fujaba/fulib-yaml-ts';

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
      expect(tGroup.students.length).equals(1);

      // add it again
      alice.tGroup = tGroup;
      expect(tGroup.students.length).equals(2);

      // add bob
      const bob = new TStudent();
      bob.name = "Bob";
      bob.studentId = "m84";

      tGroup.withStudents(alice, bob);
      expect(tGroup.students.length).equals(3);

      let idMap: YamlIdMap = new YamlIdMap();
      const yaml: string = idMap.encode([tGroup]);
      
      console.log(yaml );

      tGroup.removeYou();
  
      expect(alice.tGroup).equals(null);

      const readMap = new YamlIdMap();
      const newGroup = readMap.decode(yaml);


    });



  });