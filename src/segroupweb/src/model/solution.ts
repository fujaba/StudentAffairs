import { Achievement } from "@/model/achievement";
import { Assignment } from "@/model/assignment";

export interface Solution {
    gitUrl?: string;
    points?: string;
    achievement?: Achievement;
    assignment?: Assignment;
}