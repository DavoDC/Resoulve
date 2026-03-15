# Resoulve — Improvement Ideas

*Converted from Improvement Ideas.docx*

---

## Technical Improvements (from TODO.txt)

- Improve performance issues
- Improve deployment: make standalone EXE so users can run without installing Java (use launch4j to bundle JRE)
- Transition to better framework? (Slick2D is dead — LWJGL 3 + LibGDX are modern alternatives)

---

## Gameplay Ideas

### Map & Movement
- Make "minimal demo" where map stays the same but boss battles are added. You are a star person helping realms and you have a guide/master
- Add particle and sound effects as you go
- Recreate wiki in second GitHub and make actual repo private
- Make it so you can walk behind palms etc. — once within 1 tile radius, draw top tile on top of player

### Ice Area
- Special Ice Area = Movement slides like Pokémon gym
- DO CHECK AFTER MOVEMENT, DO ON FASTER TIMER

### Particles on Items
- Attach Particle Source to usable items — usable item showing/drawing is where particle is rendered
- Smoke near dragon
- Magic gate etherealizer pulse
- Power up particles for instant items
- Green particles for gastric gun
- Blue particles for cryocapacitors

---

## Boss Battles

Need TileState (map + cam) store, as each boss battle will be separate states

### Items Needed
- Spellbook required for magic gate — culminates crystal energy
- Second cryocapacitor — can use on Trevil or dragon?
- Needs "gyrofocuser" to keep around body and help against Viridash
- Tradeoff for stimulant = damages health, must go back to healing pool
- Wilted big flower = Poisonous
- GasMask — helps you fight mushroom
- Armor — helps you fight all
- Big Flower — increases health for minigames, infused with vitality-increasing molecules

### Trevil
- Health bar for Trevil but not his minions
- Trevil draws health from healthy trees beside him
- Minions come in rows; player has health
- Special push back: tiles change, when all different, no more push left
- Top 2/3 of screen covered in small versions of the evil tree (leftover resources)
- They slowly advance, speed up over time; make them 48x48; model columns as stacks
- Special = Root can push you back (represented as dirt tiles pushing you)

### Mycovolence
- Gross bugs and worms come from top center (leftover resources — particle emitter?)
- Touching them makes you lose health; they can be killed
- Special = You lose health over time due to mushroom spores; Gas mask makes you immune

### Viridash
- Stays in top right
- Sprays fireballs over map
- Special Attack = heat very close to it makes you lose health

### Ship
- Actual cannons surround you (leftover sprites), constantly shooting randomly
- Gunpowder barrels in corner — hit them to explode and remove nearby cannons
- You win when you destroy them all

---

## Scoring
- Global score timer during PLAY state and minigames (USE LWJGL TIMER — global, update in play state + minigames only)
- Score = time used
- Top right display: decreasing score, health, items collected

---

## Deploy & Polish
- Do full test via Exe
- Integrate Alex feedback
- Update screenshots
- Remove message from how to play on wiki
- Do promotional video = screenshot slideshow, upload to channel
- Improve based off suggestions = mini updates

---

## Extra Ideas
- Add centre crystal (5th crystal) — comes from new challenge/puzzle area in lower half of map; requires special item to enter cave
- Puzzle area = tile challenge (lava tiles appearing randomly + bubbling, must avoid)
- After Getting Gold = Space Battle: flying through space, collect cannons first; Separate to spirit realm but Ship can be same object; Background = stars moving backward
- Objective List = if you walk over obstacle zone (with lightbulb), your objective list gains an entry (e.g. "Get past trees/limestone")
- Achievements = "Seek and ye shall find" (collect all items), get high score, defeat enemies without losing health, use consumables, rewards for completion
