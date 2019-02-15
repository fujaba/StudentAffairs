import { SEClass } from "@/model/seClass";
import { Solution } from "@/model/solution";

export interface Assignment {
    task?: string;
    points?: string;
    seClass?: SEClass;
    solutions?: Solution[];
}