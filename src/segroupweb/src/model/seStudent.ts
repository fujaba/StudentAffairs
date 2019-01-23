import { SEGroup } from "@/model/seGroup";
import { Achievement } from "@/model/achievement";

export interface SEStudent {
    studentId?: string;
    teachingAssistantFor?: string;
    group?: SEGroup;
    achievements?: Achievement[];
}