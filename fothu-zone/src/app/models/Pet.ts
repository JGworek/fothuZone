import { User } from './User';

export class Pet {
    id: number;
    name: string;
    image: string;
    hunger: number;
    type: string;
    agility: number;
    strength: number;
    intelligence: number;
    level: number;
    curentXP: number;
    currentHealth: number;
    maxHealth: number;
    petLevel: number;
    owner: User;
}