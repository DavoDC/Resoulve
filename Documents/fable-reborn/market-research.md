# Market Research - ResoulveReborn Design Direction

Sources reviewed 2026-07-03 (transcripts pulled via `ytkit --format transcript`, cleaned copies kept in workspace `TEMP/yt-transcripts/` for this session - not duplicated here, this doc is the distilled findings).

## Sources

| Video | URL | Why it's here |
|---|---|---|
| "Friendslop - The New Genre That Saved Gaming" | https://youtu.be/nXBFSmoGHqY | Core genre research - defines the "friendslop" co-op design pattern David wants to draw from |
| "How Souls-Likes Took Over the Gaming World" | https://www.youtube.com/watch?v=t-asKbxASCg | Confirms explicit rejection of souls-like design (David dislikes them; also a bad fit for a one-shot build - see Feasibility below) |
| "How Good Graphics Killed Video Games" | https://youtu.be/jdVqyW7gV50 | Stylized/simple art ages better and is cheaper than realism-chasing; short focused games praised over bloated ones |
| "The Hidden Costs of Long Playtimes in Modern Gaming" | https://youtu.be/tYbotClXe_c | Confirms short, focused scope beats padded length - directly supports the "no long campaign" instinct |
| "How Lethal Company Bet on Humanity and Won" | https://youtu.be/mPmACgKcopw | Deep case study of what actually makes a friendslop game work mechanically |
| CircleToonsHD channel (`@CircleToonsHD`) | https://www.youtube.com/@CircleToonsHD/videos | Market-research channel context, not individually scraped (channel-wide download explicitly not wanted) |
| Meccha Chameleon (screenshot, Facebook post) | n/a - user-provided image | Viral proof point: 2 devs, 2 months, 10M+ Steam sales, simple concept (blend into environment, hide-and-seek), charming but simple visuals |

## Key findings, synthesized

**What makes "friendslop" games actually work (from Friendslop + Lethal Company deep-dive):**
- Proximity/context-aware voice chat as a core mechanic, not a feature bolt-on (muffled underwater, echo in canyons, tinny in metal rooms - Lethal Company's audio design is called out as revolutionary).
- **Asymmetric roles emerge from simple constraints**, not explicit class design - e.g. limited inventory slots + movement-speed penalty naturally makes players self-delegate ("shovel man," "flashlight fella," "the one watching the monitors"). Don't hand-design roles; design constraints that produce roles.
- **Self-sabotaging mechanics** - abilities/effects that hit teammates as well as enemies, items with real tradeoffs (a speed stimulant that damages health, matching Resoulve's own existing "stimulant" item design almost exactly).
- **Diegetic UI** - in-world interfaces the character physically interacts with (typing commands into a ship terminal, a physical manual, scanning and naming monsters) rather than menus/HUD popups. This reads as far more immersive and "alive" than a standard UI overlay.
- Items with **hidden secondary uses discovered through trial and error**, not explained by tutorial - part of what makes the genre feel alive and community-driven (mods and memes emerge fast when discovery is real).
- **No tutorial, minimal menus/lobby friction** - straight into the experience.
- The actual goal/objective is almost secondary to the moment-to-moment chaos and shared laughter - "moments" (a friend getting dragged into hell mid-sentence) are remembered, not quota numbers or unlocks.
- **Extremely cheap to build and buy** - PEAK made in weeks, sold huge; $7-20 price points; simple, readable art styles (not hyperrealistic) are explicitly *better* for this genre because clarity during physics chaos matters more than fidelity.

**Scope/length findings (Good Graphics + Long Playtimes videos):**
- Stylized, simple art styles age better and cost less than realism-chasing - directly validates reusing Resoulve's existing sprite assets rather than chasing higher fidelity.
- Shorter, focused experiences are consistently rated higher than padded ones ("10 hours of excellent content beats 30 hours excellent + 70 hours padding"). Directly supports the "no long campaign" instinct and the one-realm/one-boss scope already chosen.
- Games with unclear identity ("this game should have RPG elements and base building and multiplayer") are called out as a failure pattern - reinforces keeping ResoulveReborn's identity singular and clear rather than stacking unrelated systems on top of the core loop.

**Souls-like findings (explicit rejection, confirmed twice over):**
- Preference-based: David dislikes the genre.
- Feasibility-based: souls-likes are defined by an extremely specific, hard-to-nail combat *feel* (animation timing, frame data, hitbox precision) that real studios iterate on for years with human playtesting. This is a bad category to gamble a one-shot AI build on regardless of taste - it's the category most likely to feel "almost right but off," which the souls-like video itself identifies as the exact failure mode of weaker entries in the genre (the "off-brand peanut butter cups" problem).

**Commercial proof point (Meccha Chameleon):** a 2-person, 2-month project, simple concept (blend into environment, avoid seekers), charming-but-simple visuals, sold 10M+ copies on Steam. Reinforces that scope discipline and a strong simple hook beat production values for viral/commercial success in this category.

## What this changes about the ResoulveReborn brief

The original brief (written earlier today, before this research) scoped a **single-player** vertical slice: one realm, one boss (Trevil), procedurally-supplemented existing sprite assets, browser-native (Phaser 3/JS). Everything in this research **reinforces** that scope (short, focused, stylized-art-first, no grinding) - no changes needed there.

**What's newly in question: should it be co-op instead of single-player?** David's own instinct ("could Resoulve Reborn be a multiplayer game where friends solve puzzles and fight bosses together") lines up extremely well with the friendslop pattern, and Resoulve's existing lore/mechanics are a genuinely strong fit for it:
- Trevil's fight already has distinct sub-mechanics (health-drain-from-trees, advancing minion waves, limited-use root push-back) that map naturally onto asymmetric roles (someone handles minions, someone manages the push-back zone, someone manages items) - the same pattern Lethal Company uses (shovel man / flashlight fella / monitor watcher).
- The item system already has real tradeoffs (stimulant damages health, gas mask needed for Mycovolence specifically, armor helps broadly) - natural fit for "whoever's holding X handles Y" role specialization.
- The magic gate (spellbook + crystal energy) is a natural diegetic-UI/co-op-gate moment - one player operates it while others hold a position, mirroring Lethal Company's ship-terminal role.

**Decision (2026-07-03):** single-player for this build, architected forward for co-op later. Real networked multiplayer needs a server, room codes, and state sync - too much engineering risk for a one-shot bet, and hosting cost/complexity for a free hobby project is still genuinely unresolved (may be easy via modern free-tier WebSocket/WebRTC options, may not - needs its own research pass, not assumed either way). The friendslop-inspired design language (diegetic UI, discovery-based items, modular boss threats) still applies to a solo build and makes it better regardless. Trevil's three sub-mechanics (health-drain, minion waves, push-back) are built as independent systems specifically so a future co-op mode could assign them to different players without a rewrite. Multiplayer is seeded as a far-future idea in the new build's own docs, not this round's bet - see `fable-brief.md`'s final section.
