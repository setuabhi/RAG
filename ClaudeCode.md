**Context Window = "Short-Term Memory"**


    Imagine the context window as the AI's notepad or working memory.
    What it is: It is the amount of information Claude can remember at one time while working with you.
    How it works: Every time you send a message, every file Claude reads, and every command output it sees gets written onto this notepad.
    The limit: The notepad holds about 200,000 "tokens" (around 150,000 words or a mid-sized book).
    What happens when it gets full? 
        Just like a person forgets details from an hour ago if they take in too much new information, 
        Claude Code will start summarizing older parts of your conversation into conext window or can take out of context window to free up space for new actions.

**Agentic Loop = "The Try-and-Fix Work Cycle"**
Instead of just answering your question with text and waiting for you to do the work, Claude Code acts like an autonomous worker in a loop:

    ┌───────────────────────────┐
    │  1. Look at the problem   │
    └─────────────┬─────────────┘
                  │
                  ▼
    ┌───────────────────────────┐
    │    2. Try a fix/command   │
    └─────────────┬─────────────┘
                  │
                  ▼
    ┌───────────────────────────┐
    │   3. Check the result     │
    └─────────────┬─────────────┘
                  │
        Did it work?
        ├── NO  ──► [Loop back to Step 1 & try another way]
        └── YES ──► [Done! Return answer to you]