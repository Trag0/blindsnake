# Blind Snake on a 2D map (Torus) Java

## Before starting there are some assumptions made:

- 1 apple is enough to win 
- Apple cannot be generated after the snake moves. So it is already predefined with any location on the map, and cannot be generated at any location the snake has already visited.
- The map grid is a 2D space that can warp. This is the definition for a torus map, and the approach is also through traversal on a torus, which almost always resembles the hamiltonian cycle.
- There were no specification about the snake hitting its own body. But given the old nokia style game, it is impossible to hit it, because of the snake not increasing the size without any apples eaten.
- 


### My original thought was a spiral. The snake would just continue a simple spiral around itself until it traverses the whole map (torus). But it usually does not do it efficient enough and falls behind due to the predefined cap of 35S. 

That is why this repo is comparing 2 approaches. The simple spiral around yourself and a hamiltionian circle approach with a drift(x,y).


### Before starting with the task, I started with a brief idea in mind, of this task being mathematically close to the hamiltonkreis (graph) and after some research and got some interesting insights about the possible implementation. 

Here is an interesting source that led me to a very simple solution: 

https://gamedev.stackexchange.com/questions/133460/how-to-find-a-safe-path-for-an-ai-snakeIt


30 steps RIGHT followed by 1 step DOWN, then repeat forever.

### Intuition on a torus: repeating a fixed macro is equivalent to walking around the grid along a discrete line of net drift (Δx,Δy) = (30, 1). If that drift “wraps around” with full period S=A·B, it generates all cells before repeating, then the path is a Hamiltonian cycle. you visit every cell exactly once before coming back to the starting cell. In that case you will hit the apple in less than S button press.

- This happens for about 95% of A x B boards, because you only fail when gcd(A,B) has a prime >= 7. Random pairs usually do not share those bigger primes. 

∏​(1−1/p^2​)≈0.95

Benefits are:
- Constant memory, constant code size.
- About one button press per visited cell.
- Always under the 35*S budget even on huge boards.

the other ~5%
- If gcd(A,B) has a prime >= 7 (both divisible by 7), the (30,1) drift splits the grid into multiple cycles. If the apple is not in the cycle, you will miss it before the cap.

- upgrade option is to join a few short drifts (like (16,1)or (21,1)) to cover more cases, still respecting the 35*S budget

similar ideas
- safe snake paths, and there are discussions mapping these patterns to Hamiltonian cycles on toroidal grids. This approach is basically one of those simple drifts that gives a nearly uniform scan.




SPOILER

The hamiltonian approach wins almost every single time. The spiral taken as the default or baseline has a win rate of 5-10% only.


