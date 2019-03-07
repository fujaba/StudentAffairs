
import TheoryGroup from './tGroupModel/TheoryGroup';
import TheoryStudent from './tGroupModel/TheoryStudent';


import { Yamler, YamlIdMap } from '@fujaba/fulib-yaml-ts';
import Seminar from './tGroupModel/Seminar';
import Presentation from './tGroupModel/Presentation';
import ObjectDiagrams from './Fulib/ObjectDiagrams';

import * as Viz from 'viz.js';
import { fstat } from 'fs';
import * as fs from 'fs';

const path = require('path');
const Worker = require('tiny-worker');

test('tGroupmodel', async () => {
      const tGroup = new TheoryGroup();
      expect(tGroup).not.toBe(null);

      tGroup.head = "Martin";
      expect(tGroup.head).toBe("Martin");

      const alice = new TheoryStudent();
      expect(alice).toBeTruthy();
      alice.name = "Alice";
      alice.studentId = "m42";

      const carli = new TheoryStudent();
      carli.name = "Carli";
      carli.studentId = "m23";

      tGroup.withStudents(alice, carli);
      expect(tGroup.students).toContain(alice);
      expect(alice.group).toBe(tGroup);
      expect(carli.group).toBe(tGroup);

      alice.group = null;

      expect(tGroup.students).not.toContain(alice);
      expect(tGroup.students.length).toBe(1);

      // add it again
      alice.group = tGroup;
      expect(tGroup.students.length).toBe(2);

      // add bob
      const bob = new TheoryStudent();
      bob.name = "Bob";
      bob.studentId = "m84";

      tGroup.withStudents(alice, bob);
      expect(tGroup.students.length).toBe(3);

      // add seminar
      const modelChecking = new Seminar();
      modelChecking.topic = "Model Checking";
      tGroup.withSeminars(modelChecking);

      const present1 = new Presentation();
      present1.content = "cool stuff";
      modelChecking.withPresentations(present1);
      alice.withPresentations(present1);

      let idMap: YamlIdMap = new YamlIdMap();
      const yaml: string = idMap.encode([tGroup]);
      
      // console.log(yaml );

      const od = new ObjectDiagrams();
      const dot = od.dump(tGroup);
      console.log(dot);

      const worker =  new Worker(path.resolve(__dirname, '../node_modules/viz.js/full.render.js'));
      const viz = new Viz({ worker });
      const result = await viz.renderString(dot);
      worker.terminate();
      console.log(result); 
      if ( ! fs.existsSync('tmp'))
      {
        fs.mkdirSync('tmp');
      } 
      
      await fs.writeFile('tmp/objDiag.svg', result, err => { console.log(err); });
      

      tGroup.removeYou();
  
      expect(alice.group).toBe(null);

      const readMap = new YamlIdMap();
      // const newGroup = readMap.decode(yaml);
  });