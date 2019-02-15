import { SEClass } from "@/model/seClass";
import { SEStudent } from "@/model/seStudent";
import { Solution } from "@/model/solution";

export interface Achievement {
    grade?: string;
    officeStatus?: string;
    seClass?: SEClass;
    student?: SEStudent;
    solutions?: Solution[];
}