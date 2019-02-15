import { SEGroup } from "@/model/seGroup";
import { Achievement } from "@/model/achievement";
import { Assignment } from "@/model/assignment";

export interface SEClass {
    topic?: string;
    term?: string;
    group?: SEGroup;
    assignments?: Assignment[];
    participations?: Achievement[];
}