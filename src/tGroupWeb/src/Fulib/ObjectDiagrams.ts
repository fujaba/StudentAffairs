
import { Yamler, YamlIdMap }  from "@fujaba/fulib-yaml-ts";
import { stringify } from "querystring";
import { Reflector } from "@fujaba/fulib-yaml-ts/lib/yamler/util";

export default class ObjectDiagrams {
    public dump(root: Object): string {

        const idMap = new YamlIdMap();
        const diagramObjects = idMap.collectObjects([root]);
        const edgesMap = new Map<string, string[]>();

        const nodesString = this.makeNodes(diagramObjects, idMap, edgesMap);

        const edgesString = this.makeEdges(edgesMap);

        const dotString = "" +
        "digraph H {\n" +
        // "rankdir=BT\n" +
        `${nodesString} \n` +
        `${edgesString} \n` +
        "}\n";

        return dotString;
    } 


    makeEdges(edgesMap: Map<string, string[]>): string {
        let buf: string = "";

        for (let entry of edgesMap.entries()) {
            const key = entry[0];
            const edge: string[] = entry[1];

            buf = buf + `   ${edge[0]} -> ${edge[1]}` + 
                " [arrowhead=none fontsize=\"10\" " +
                    "taillabel=\"" + edge[2].substring(1) + "\" " +
                    "headlabel=\"" + edge[3].substring(1) + "\"];\n";
        }

        return buf;
    }

    makeNodes(diagramObjects: Object[], idMap: YamlIdMap, edgesMap: Map<string, string[]>): string {

        let buf: string = "";

        for (let entry of idMap.objIdMap.entries()) 
        {
            const key = entry[0];
            const obj = entry[1];

            if ( ! diagramObjects.some(e => e === obj)) continue;

            const className = obj.constructor.name;

            buf = buf + key + " " +
               "[\n" +
               "   shape=plaintext\n" +
               "   fontsize=\"10\"\n" +
               "   label=<\n"  +
               "     <table border='0' cellborder='1' cellspacing='0'>\n" +
               "       <tr><td>" + 
               "<u>" + key + " :" + className + "</u>" + 
               "</td></tr>\n"  +
               "       <tr><td>";

            // loop through properties of this object
            const reflector: Reflector = idMap.reflectorMap.getReflector('', obj);

            if (reflector)
            {
                for (const prop of reflector.getProperties()) {
                    const value: any = reflector.getValue(obj, prop);

                    if ( ! value) {
                        continue;
                    }
                    else if (value instanceof Array) {
                        for( let elem of (value as any[])) {
                            this.addEdge(edgesMap, idMap, obj, prop, elem);
                        }
                        continue;
                    } 
                    else if (typeof value === 'object') {
                        this.addEdge(edgesMap, idMap, obj, prop, value);
                        continue;
                    }
                    else {
                        buf = buf + "  " +
                            prop + ' = ' +  value +
                            "<br  align='left'/>\n";
                    }
                }
            }
        
            buf = buf + "</td></tr>\n" +
            "     </table>\n" +
            "  >];\n"   

            const result = buf;
        }

        return buf;
    }


    addEdge(edgesMap: Map<string, string[]>, idMap: YamlIdMap, src: any, prop: string, tgt: any): void {
        const srcKey: string = idMap.idObjMap.get(src);
        const tgtKey: string = idMap.idObjMap.get(tgt);

        if (srcKey <= tgtKey) {
            const edgeKey = srcKey + '>' + tgtKey;
            let edge: string[] = edgesMap.get(edgeKey);
            if ( ! edge) {
                edge = [srcKey, tgtKey, "", ""];
                edgesMap.set(edgeKey, edge);
            }
            const headLabel = edge[3] + prop + "\\n";
            edge[3] = headLabel;
        }
        else{
            const edgeKey = tgtKey + '>' + srcKey;
            let edge: string[] = edgesMap.get(edgeKey);
            if ( ! edge) {
                edge = [tgtKey, srcKey, "", ""];
                edgesMap.set(edgeKey, edge);
            }
            const tailLabel = edge[2] + prop + "\\n";
            edge[2] = tailLabel;
        }
    }
}