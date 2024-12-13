// Simple algorithm to design a room
// 1. Divide capacity by 2 and allocate it with islands (8 seats)
// 2. Fill remaining seats with tables (4 seats)
// 3. Arrange in the pattern: island, table, table, island, table, table, island... etc
export const generateRoomItems = (capacity) => {
    const roomItems = [];
    let idCounter = 1;

    // Divide capacity by 2
    const totalHalf = Math.floor(capacity / 2);

    // How many islands can we put into it?
    const numIslands = Math.floor(totalHalf / 8);

    // How many seats left for tables:
    const remainingCapacity = capacity - (numIslands * 8);

    // Remaining seats covered by tables (some might be empty):
    const numberOfTables = Math.ceil(remainingCapacity / 4);

    // Step 3: Distribute using the pattern: island, table, table
    let remainingIslands = numIslands;
    let remainingTables = numberOfTables;

    while (remainingIslands > 0 || remainingTables > 0) {

        // Place an island if available
        if (remainingIslands > 0) {
            roomItems.push({id: idCounter++, type: 'island'});
            remainingIslands--;
        }

        // Place two tables if available
        for (let i = 0; i < 2 && remainingTables > 0; i++) {
            roomItems.push({id: idCounter++, type: 'table'});
            remainingTables--;
        }
    }

    return roomItems;
}
