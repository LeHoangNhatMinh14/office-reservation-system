import React, {useState, useCallback} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import Room from './Room.jsx';
import ControlPanel from './ControlPanel.jsx';
import styles from './RoomDesigner.module.css';
import {createDragDropManager} from "dnd-core";
import RoomCalls from "../../api calls/RoomCalls.jsx";

const RoomDesigner = ({room, refreshRooms}) => {

    const TABLE_CAPACITY = 4;
    const ISLAND_CAPACITY = 8;

    const [roomData, setRoomData] = useState(room);

    const getCapacity = () => {
        return roomData?.tables?.map(item => item.tableType === "SMALL_TABLE" ? TABLE_CAPACITY : ISLAND_CAPACITY)
            .reduce((accumulator, currentValue) => accumulator + currentValue, 0);
    }

    const addTable = useCallback((type) => {
        const newTable = {
            id: Date.now().toString(),
            tableType: type,
            horizontalPosition: 50,
            verticalPosition: 50,
            width: type === 'SMALL_TABLE' ? 100 : 200,
            height: 100,
        };

        setRoomData(prev => ({
            ...prev,
            tables: [...prev.tables, newTable],
        }));

    }, [roomData.tables]);

    const updateTable = useCallback((updatedItem) => {
        setRoomData(prev => ({
            ...prev,
            tables: prev.tables.map(item =>
                item.id === updatedItem.id ? updatedItem : item
            ),
        }));
    }, []);

    const deleteTable = useCallback((id) => {
        setRoomData(prev => ({
            ...prev,
            tables: prev.tables.filter(item => item.id !== id),
        }));
    }, []);

    const updateRoomSize = useCallback((width, height) => {
        setRoomData(prev => ({...prev, width, height}));
    }, []);

    const saveRoomData = useCallback(() => {
        RoomCalls.updateRoom(room.id, roomData).then(() => refreshRooms())
    }, [roomData]);

    return (
        <DndProvider backend={HTML5Backend} manager={createDragDropManager(HTML5Backend)}>
            <div className={styles.container}>
                <ControlPanel
                    addTable={addTable}
                    updateRoomSize={updateRoomSize}
                    roomWidth={roomData.width}
                    roomHeight={roomData.height}
                    saveRoomData={saveRoomData}
                    capacity={getCapacity()}
                />
                <Room
                    width={roomData.width}
                    height={roomData.height}
                    tables={roomData.tables}
                    updateTable={updateTable}
                    deleteTable={deleteTable}
                />
            </div>
        </DndProvider>
    );
};

export default RoomDesigner;

