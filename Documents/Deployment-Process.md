# Resoulve — Deployment Process

*Converted from Deployment Process.docx*

---

1. Check version string in globals
2. Run Codacy and fix up code
3. Clean and build in NetBeans twice
4. Delete components next to `.exe` and run `updateComponents` script
5. Run launch4j and create `.exe` — see `ExecutionConfiguration` for settings
6. Delete "launch4j log" next to executable
7. Test executable
8. Copy deployable folder to Downloads
9. Rename to `Resoulve VX-X-X.zip`
10. Compress into ZIP using special settings (see image in Documents/)
11. Publish as release on GitHub
12. Update Changelog
13. Send to: Discord server, Sam, Victor, Jazz, Ben, Oliver, Wendy, Nisarg, Slick
