import { User } from './User';
import { Image } from './Image';

export class Pet {
    id: number;
    name: string;
    image: Image;
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