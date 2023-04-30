create table pets.attacking_battle_pets (id  bigserial not null, alive_status boolean, current_health int4, battle_id int8, pet_id int8, primary key (id))
create table pets.battles (id  bigserial not null, battle_finished boolean, battle_type varchar(255), created_on timestamp default CURRENT_TIMESTAMP, max_number_of_attacking_pets int4, max_number_of_defending_pets int4, attacking_user_id int8, defending_user_id int8, losing_user_id int8, next_turn_user_id int8, winning_user_id int8, primary key (id))
create table pets.challenge_requests (id  bigserial not null, accepted_status boolean, created_on timestamp default CURRENT_TIMESTAMP, max_number_of_attacking_pets int4, max_number_of_defending_pets int4, rejected_status boolean, attacking_user_id int8, defending_user_id int8, resulting_battle_id int8, primary key (id))
create table pets.defending_battle_pets (id  bigserial not null, alive_status boolean, current_health int4, battle_id int8, pet_id int8, primary key (id))
create table pets.dungeons (id  bigserial not null, dungeon_name varchar(255), boss_pet_id int8, primary key (id))
create table pets.floors (id  bigserial not null, boss_room int4, enemy_level int4, floor_number int4, ladder_room int4, monster_spawn_rate int4, starting_room int4, dungeon_id int8, mini_boss_pet_id int8, primary key (id))
create table pets.floors_non_barren_cells (floor_id int8 not null, non_barren_cells int4)
create table pets.images (id  bigserial not null, image_url varchar(255), primary key (id))
create table pets.pets (id  bigserial not null, agility int4, available_level_ups int4, current_health int4, current_xp int4, hunger int4, intelligence int4, max_health int4, name varchar(255), pet_level int4, strength int4, stat_type varchar(255), image_id int8, owner_id int8, user_id int8, primary key (id))
create table pets.supported_user_colors (colors varchar(255) not null, primary key (colors))
create table pets.turns (id  bigserial not null, attacker_replaced_dead_pet boolean, attacking_pet_accuracy_modifier float8, attacking_pet_armor_modifier float8, attacking_pet_attack_modifier float8, attacking_pet_current_health int4, attacking_pet_evasion_modifier float8, battle_finished boolean, created_on timestamp default CURRENT_TIMESTAMP, defender_replaced_dead_pet boolean, defending_pet_accuracy_modifier float8, defending_pet_armor_modifier float8, defending_pet_attack_modifier float8, defending_pet_current_health int4, defending_pet_evasion_modifier float8, turn_flavor_text varchar(255), turn_number int4, turn_technical_text varchar(255), attacking_pet_id int8, battle_id int8, defending_pet_id int8, primary key (id))
create table pets.users (id  bigserial not null, admin_status boolean, email_address varchar(255), favorite_color varchar(255), user_password varchar(255), username varchar(255), verified_status boolean, secret_password varchar(255), primary key (id))
create table pets.verification_tokens (id  bigserial not null, verification_token varchar(255), user_id int8, primary key (id))
create table pets.xp_chart (pet_level  serial not null, xp_to_next_level int4, primary key (pet_level))
create sequence hibernate_sequence start 1 increment 1
create table page (id int4 not null, early_story_end boolean, final_story_end boolean, option_one_page_number int4, option_one_text varchar(255), option_three_page_number int4, option_three_text varchar(255), option_two_page_number int4, option_two_text varchar(255), page_number int4, story_text varchar(255), primary key (id))
alter table if exists pets.attacking_battle_pets add constraint FKfd99sbrr3sam0bxckxj7ev5fp foreign key (battle_id) references pets.battles
alter table if exists pets.attacking_battle_pets add constraint FKa599qf8mwx1gh09sn3kyhpq0n foreign key (pet_id) references pets.pets
alter table if exists pets.battles add constraint FK66qkoth8ul9hxvfnsoeiuaqew foreign key (attacking_user_id) references pets.users
alter table if exists pets.battles add constraint FKhaeloeap5xa8s9dvq2cqgau5m foreign key (defending_user_id) references pets.users
alter table if exists pets.battles add constraint FKppjv19ok7rgfcfvsx9s724645 foreign key (losing_user_id) references pets.users
alter table if exists pets.battles add constraint FKeug6d4c4c6es0s4v4hwo27jjm foreign key (next_turn_user_id) references pets.users
alter table if exists pets.battles add constraint FK2s831jqmd37qe411rb7n1f3kd foreign key (winning_user_id) references pets.users
alter table if exists pets.challenge_requests add constraint FKgav4xfb7wcls2i5olwd2lfmbt foreign key (attacking_user_id) references pets.users
alter table if exists pets.challenge_requests add constraint FKqoe033nvulmlxq0xvk0wc5fp6 foreign key (defending_user_id) references pets.users
alter table if exists pets.challenge_requests add constraint FKbxk5n0w1vqn7lyxhfq3rmxrta foreign key (resulting_battle_id) references pets.battles
alter table if exists pets.defending_battle_pets add constraint FK7f3ghl5y6xw71gnw40i6261i8 foreign key (battle_id) references pets.battles
alter table if exists pets.defending_battle_pets add constraint FKli7wls2631h3u2u4wc6rs35qy foreign key (pet_id) references pets.pets
alter table if exists pets.dungeons add constraint FKgpxhjj0yc091lld2w0kjlan79 foreign key (boss_pet_id) references pets.pets
alter table if exists pets.floors add constraint FKqu1y8fq60292txlqag83v9pj8 foreign key (dungeon_id) references pets.dungeons
alter table if exists pets.floors add constraint FKgow6vg1bkl1x3v0f7ljeek8rv foreign key (mini_boss_pet_id) references pets.pets
alter table if exists pets.floors_non_barren_cells add constraint FK50uqfw9jqxkvrdbnafed9htqo foreign key (floor_id) references pets.floors
alter table if exists pets.pets add constraint FKft4451ihsepaytxr1gati6f5o foreign key (image_id) references pets.images
alter table if exists pets.pets add constraint FKc47kjb41qf50bwgddm024m5xn foreign key (user_id) references pets.users
alter table if exists pets.turns add constraint FK26b4l617hudgodlftwjnb07j5 foreign key (attacking_pet_id) references pets.pets
alter table if exists pets.turns add constraint FK636yemiv2m2wsxuwbmp6cvqcs foreign key (battle_id) references pets.battles
alter table if exists pets.turns add constraint FK9txt30idly3i40fesuptr44rg foreign key (defending_pet_id) references pets.pets
alter table if exists pets.verification_tokens add constraint FK54y8mqsnq1rtyf581sfmrbp4f foreign key (user_id) references pets.users
