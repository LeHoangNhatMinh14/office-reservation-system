export const parseRoomsResponse = (rooms) => {
    return rooms.map(room => ({
        ...room, tables: room.tables.map(table => {
            if (table.tableType === "SMALL_TABLE") {
                return {...table, width: 100, height: 100}
            } else return {...table, width: 200, height: 100}
        })
    }));
}