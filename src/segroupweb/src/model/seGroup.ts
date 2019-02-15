import { SEClass } from "@/model/seClass";
import { SEStudent } from "@/model/seStudent";

export interface SEGroup {
    head?: string;
    classes?: SEClass[];
    students?: SEStudent[];
}