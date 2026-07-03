# Brief for Fable: ResoulveReborn (vertical slice)

**Everything you need is in this repo and in this document.** You should not need to look outside `C:\Users\David\GitHubRepos\Resoulve`, and you should not need further web research - the tech stack, scope boundaries, and asset strategy are already decided below. Your job is **building one small, complete, polished slice - not researching, not redesigning the boundaries, not attempting the whole game.**

Start here, then read (all in this repo):
- `Documents/IDEAS.md` - David's own design notes: boss mechanics, item list, gameplay ideas. Source of truth for lore/mechanics below.
- `Documents/Claude-Feedback.md` - an honest prior assessment of the original Java build (why it never released, what would matter most).
- `Documents/fable-reborn/market-research.md` - genre research (the "friendslop" co-op design pattern - Lethal Company, Peak, etc.) that shaped the design language below, even though this build is single-player. Read it for the *why* behind diegetic UI, discovery-based items, and modular boss-threat design.
- `Project/src/` - the original Java/Slick2D implementation. Read it as a **mechanics reference** (how combat, inventory, states, particles work), not as code to port line-by-line - you're rebuilding in a different stack.
- `ExtraResources/` - **real, existing sprite art** for enemies, items, cannon, and effect beams (510 files). This is not a from-scratch art job - see Asset Strategy below.

**Single-player this round, but architect for co-op later - do not build multiplayer.** David's original instinct was "could this be multiplayer, friends solving puzzles and fighting bosses together" - the design research (see market-research.md) confirms Resoulve's lore/mechanics are a genuinely strong fit for that. But real networked multiplayer needs a server, room codes, and state sync - a much bigger, riskier build than a one-shot should gamble on, and hosting cost/complexity for a free hobby project is still an open question. **Decision: build single-player now, but keep the boss encounter's sub-systems modular** (minion waves, push-back zone, health-drain-from-trees as separate, independently-triggered systems rather than one tangled Trevil update loop) so that a future co-op mode could assign different systems to different players without a rewrite. Don't build any networking code, don't add multiplayer UI/menus this round - just don't paint yourself into a single-hardcoded-player-object corner where splitting responsibilities later would require re-architecting everything. Document the multiplayer idea as a far-future item in this project's own `IDEAS.md`/`HISTORY.md` once you create them (see Documentation section near the end) - it's a real idea, just not this round's bet.

**Window:** Fable 5 promotional access ends 2026-07-07 11:59:59 PM PT. Weekly Fable usage is healthy (not a scarcity situation) - this is a deliberate second major push alongside the AudioManager GUI work, not a replacement for it.

---

## What Resoulve is, and why "Reborn" instead of "port"

Resoulve is David's own 2D action-adventure game (Java + Slick2D, built 2019-2021 while learning game dev): a star-person protagonist explores realms with a guide/master figure, collects 5 crystals, fights named bosses, and opens a magic gate. Real architecture (state machine, custom audio/particle/font servers), genuine gameplay loop, a GitHub wiki, screenshots, and feedback from real playtesters (Alex, Spi).

**It was never released**, for two documented reasons (see `Claude-Feedback.md`):
1. **Install friction.** Requires a Java install; most casual players won't bother. No browser version, no single-click exe with bundled JRE.
2. **Dead framework.** Slick2D hasn't been updated since ~2013 - no community, no fixes, technical debt ceiling.
3. (Related) **Boss battles were designed but never implemented** - the game is architecturally incomplete.

**ResoulveReborn is not "port the Java code to a new language."** It's "rebuild the same world and lore in a stack that solves both structural problems at once" - a browser-native build removes install friction entirely (open a URL, you're playing), and a modern actively-maintained framework removes the bitrot risk. This is the single most important thing to get right: **same world, same characters, same lore - different, better-suited technology.**

---

## Tech stack: Phaser 3 (JavaScript) - decided, do not re-litigate

**Decision, confirmed with David 2026-07-03:** Phaser 3, running entirely in-browser, no server component required for this slice (single-player, no multiplayer this round). This is a deliberate exception to David's normal "avoid JavaScript" preference for home projects - approved specifically for this project because the entire value proposition (zero-install, instant browser play) is a JS/web-canvas-ecosystem strength that Python cannot match without reintroducing the exact friction problem above (Pygame doesn't run in-browser without heavy WASM workarounds that undercut the goal itself).

**Why Phaser specifically:** the most mature, actively-maintained, code-first 2D web game framework. Canvas/WebGL rendering, built-in physics (arcade physics is enough for this slice - no need for Matter.js), sprite atlases, tilemaps, particle emitters, and WebAudio all first-class. No build step is required to get something running (a single HTML file + Phaser via CDN + your JS is enough for a slice this size) - keep it that simple unless you hit a real reason not to.

**Reference point (why this pattern, not invented from nothing):** `World of Claudecraft` (Fable 5, 2026-06) used Three.js with procedurally-generated geometry/textures/audio to ship a genuinely impressive browser-native multiplayer game in one build session. The transferable lesson isn't "clone WoW" - it's "pick a browser-native framework, avoid an asset pipeline that could block a one-shot build, and get something that actually *runs* end-to-end before adding breadth." Phaser is that same pattern for 2D.

---

## Asset strategy: reuse what exists, generate what's missing

**Unlike ClaudeCraft, you do not need to generate all art procedurally from scratch - real sprite assets already exist and should be your foundation:**

- `ExtraResources/Enemies/` - dragon, mushroom, tree (Trevil's evil-tree form) sprites already exist and match the boss lore below.
- `ExtraResources/Items/Magic Items/` - dozens of beam/haste effect sprites (acid, blue, eerie, jade, magenta, orange, red, royal, sky variants) - use these for the crystal/spell-effect visuals rather than generating new ones.
- `ExtraResources/Cannon/`, `ExtraResources/Player/`, `ExtraResources/UI/`, `ExtraResources/Orbz/`, `ExtraResources/SoundFX/` - player sprites, UI elements, orb/crystal visuals, and sound effects.

**Your job:** adapt/re-export these into Phaser-friendly formats (spritesheets/atlases as needed), and use **procedural generation only to fill genuine gaps** - particle effects (Phaser's particle emitter, not pre-rendered particle sprites), screen shake/juice, dynamic lighting/glow around crystals and the magic gate, tile-based terrain if no matching tileset exists in `ExtraResources`. Don't discard real assets in favor of procedural versions for their own sake - that's solving a problem (no assets) that this project doesn't actually have.

If `SoundFX/` doesn't cover everything needed (ambient realm music, UI sounds), synthesize the gaps with WebAudio (oscillators/noise, the same technique ClaudeCraft used) rather than sourcing new audio files.

---

## Scope: ONE realm, ONE boss, fully realized - not a remake

This is the single most important constraint. The original game's problem was never "not enough content designed" (bosses were fully speced) - it was "never finished, never shipped." Don't repeat that failure mode by attempting the whole game in one Fable session. A small, complete, polished slice beats a broad, half-working remake.

### IN scope

- **One realm/map** - a self-contained explorable area (pick one from the original Java `Play`/`SpRealm` states as reference, or design an equivalent-scale new one matching the tone).
- **Core movement + exploration loop** - the star-person protagonist walks the realm, collects items, interacts with the guide/master figure, reaches a boss encounter. Include the "walk behind palms/tall objects" depth-sorting detail from `IDEAS.md` - it's a small, real polish touch worth keeping.
- **Inventory + item system** - at minimum: a crystal (the core collectible), and 2-3 of the items from `IDEAS.md`'s item list (see Lore below) that are relevant to reaching/beating the boss.
- **One complete boss fight: Trevil.** Chosen over Mycovolence/Viridash/Ship because it's the most self-contained and mechanically interesting for a first slice (see full mechanic below) and has an existing matching sprite (`tree full.png`).
- **Particle effects** - smoke, magic-gate pulse, item pickup glow, crystal energy - per the particle ideas already documented in `IDEAS.md`. This is cheap in Phaser and was explicitly called out as adding life to the world.
- **A simple win/lose state** - defeating Trevil ends the slice with a clear "you win" screen; running out of health restarts the encounter. No need for a full game-over/menu system beyond this.
- **Runs from a single HTML file, opens directly in a browser, no install, no build step required to play it.**

### Design language: friendslop-inspired, even though this build is solo

The genre research in `market-research.md` (Lethal Company, Peak, and the broader "friendslop" pattern) isn't just about co-op - several of its principles make a *solo* game feel more alive too, and are cheap to apply here:

- **Diegetic UI over menu overlays.** The magic gate (spellbook + crystal) is a natural moment for this - the player character physically interacts with the gate (walks up, an in-world animation plays, the gate visibly responds) rather than a popup menu saying "Use Spellbook?". Apply the same instinct to the inventory if practical - an in-world satchel/pouch the character visibly opens, not a detached UI panel floating over the game.
- **Discovery over tutorial.** No tutorial popups explaining what items do. Let the player find out the flower heals by using it, find out the spellbook's role by trying it at the gate. A brief in-world flavor text (a sign, a note from the guide figure) is fine; a mechanics tutorial screen is not.
- **Items with real tradeoffs, not pure upgrades** - matches Resoulve's own existing design (the stimulant item in `IDEAS.md` already damages health as a tradeoff for speed). Keep that spirit for whatever items you include - a cost attached to the benefit, not a free power-up.
- **Modular boss threats** (see Trevil below) - independent systems the player must juggle simultaneously, not one linear damage-sponge fight.

### OUT of scope (explicitly - do not build these this round)

- Multiplayer, accounts, persistence/save system - single-player, single-session only.
- The other 4 crystals, other realms, other bosses (Mycovolence, Viridash, Ship/space-battle) - documented in `IDEAS.md` as a real roadmap for a *later* round, not this one.
- The minigames/challenges system (`Challenge.java`, `CannonDodge.java`) - separate feature, not needed for this slice.
- Achievements, scoring/global timer, settings menu - polish-tier, not core-loop.
- Mobile/touch controls - keyboard-only is fine for this slice.

---

## Lore and mechanics reference (from `Documents/IDEAS.md` and the original source)

**World:** the player is a star-person helping realms, guided by a master/guide figure. Multiple realms exist; each realm has its own environmental theme (the original had an Ice Area with Pokemon-gym-style sliding movement, for reference on tone - not required for this slice).

**Crystals:** 5 total across the full game, each tied to a realm/challenge. This slice needs only the one relevant to the chosen realm/Trevil encounter.

**The magic gate:** requires a spellbook to open, "culminates crystal energy" - a good visual/narrative beat for reaching the boss encounter (walk up, use spellbook + crystal, gate opens, boss area unlocked).

**Items relevant to a Trevil-focused slice** (full list in `IDEAS.md` if useful for flavor, but don't build all of them):
- **Spellbook** - required for the magic gate.
- **Armor** - "helps you fight all" - a general defensive item, reasonable to include.
- **Big Flower** - "increases health... infused with vitality-increasing molecules" - a healing pickup, matches Trevil's own health-draining mechanic thematically (he drains from healthy trees; you're drained by proximity to his corruption - a flower that restores vitality is a neat narrative rhyme).

**Trevil (the boss for this slice):**
- A corrupted/evil tree. Sprite reference: `ExtraResources/Enemies/tree full.png` (his full boss form) and `tree.png` (regular trees around him, possibly the "leftover resources" advancing minions described below).
- **Health mechanic:** Trevil draws health from healthy trees beside him - i.e. his effective HP regenerates or is boosted while nearby trees remain alive/present. A real design choice: does the player need to clear surrounding trees first, or is this a passive drain the player must out-damage? Your call - document the choice.
- **Minions:** small versions of "the evil tree" cover the top 2/3 of the screen as leftover resources, arranged in rows, slowly advancing and speeding up over time. Suggested scale from the original notes: model at 48x48, arranged in column-stacks.
- **Special attack - Root push-back:** represented as dirt tiles pushing the player back; described as having a "no more push left" state once all tiles have changed - i.e. a limited-use crowd-control move, not infinite.
- **Player has a health bar; Trevil has a health bar; his minions do not** (per the original design note) - keep the UI reflecting that distinction.

This is a genuinely interesting boss (health-drain-from-environment mechanic, advancing minion waves, a limited-use crowd-control special) - there's real design substance here worth doing justice to, not just re-skinning a generic top-down shooter enemy.

**Architecture note (forward-looking, not multiplayer this round):** implement the three Trevil sub-mechanics - health-drain-from-trees, minion wave advance, root push-back - as three independent systems/classes with their own update logic and state, coordinated by (not tangled into) a top-level encounter controller. This is purely a solo-player fight this round - the reason for the separation is that if co-op is ever added later, each system is a natural candidate for "assign this threat to a player" without requiring a rewrite. Don't build anything multiplayer-specific (no networking, no second-player input handling) - just don't hardcode the assumption that one player must handle all three simultaneously in a way that's structurally hard to split later.

---

## Definition of done

- [ ] Opens and plays in a browser from a single entry point (e.g. `index.html`), no install, no build step for the player.
- [ ] One realm is explorable: movement, item pickups, depth-sorted foliage (walk-behind effect), a path to the boss encounter.
- [ ] Inventory shows at least the crystal + 2-3 items from the list above; items are usable (spellbook opens the gate; armor/flower have a visible effect).
- [ ] Trevil boss fight is fully playable start to finish: his health-drain mechanic, advancing minion waves, and the root push-back special are all implemented and feel distinct from each other - not just "enemy with a health bar."
- [ ] Real sprite assets from `ExtraResources/` are used as the visual foundation; procedural generation fills genuine gaps only (particles, glow/lighting, any missing terrain/audio).
- [ ] A clear win state on defeating Trevil.
- [ ] Runs at a stable frame rate with no console errors - verify by actually playing it, not just reading the code.

## Go beyond the baseline (optional, if time remains after Definition of Done)

You have explicit license to push polish once the slice above is solid: screen shake and hit-flash on combat, ambient particle life in the realm (falling leaves, dust motes), a title/intro screen with the game's name, smoother camera follow, juice on pickups (bounce/scale-in). Do not expand scope (more realms/bosses/items) - depth of polish on the slice above, not breadth, is the goal.

## Document as you go

If you make a real design call not already decided here (e.g. how Trevil's health-drain actually triggers, exact tile layout of the realm), write it into `Documents/HISTORY.md` (create if it doesn't exist) the same way AudioManager's Fable session documented its own decisions - so this is traceable later, not lost.

**Create a proper far-future ideas doc for this build too** (`Documents/HISTORY.md` for decisions made, and add a "Far Future" section - either in that file or a new `Documents/fable-reborn/IDEAS.md`, your call on which fits better once you see how much you've written). Seed it with:
- **Multiplayer/co-op** - the real idea behind this build's modular boss-threat architecture. Note explicitly that it's contingent on resolving hosting cost/complexity for a free hobby project (unresolved as of this session - modern low-cost options like a free-tier WebSocket relay or peer-to-peer WebRTC may make this easier than it sounds, but that needs its own research pass before committing).
- The other 4 crystals, realms, and bosses (Mycovolence, Viridash, Ship) cut from this round's scope.
- Anything else you consciously deferred while building.
