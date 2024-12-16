import React, {useState, useCallback} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import Room from './Room.jsx';
import ControlPanel from './ControlPanel.jsx';
import styles from './RoomDesigner.module.css';
import {createDragDropManager} from "dnd-core";

const RoomDesigner = () => {

    const TABLE_CAPACITY = 4;
    const ISLAND_CAPACITY = 8;

    const [roomData, setRoomData] = useState({
        width: 800,
        height: 600,
        furniture: [],
    });

    const getCapacity = () => {
        return roomData?.furniture?.map(item => item.type === "table" ? TABLE_CAPACITY : ISLAND_CAPACITY)
            .reduce((accumulator, currentValue) => accumulator + currentValue, 0);
    }

    const addFurniture = useCallback((type) => {
        const newFurniture = {
            id: Date.now().toString(),
            type,
            x: 50,
            y: 50,
            width: type === 'table' ? 100 : 200,
            height: 100,
        };

        setRoomData(prev => ({
            ...prev,
            furniture: [...prev.furniture, newFurniture],
        }));

    }, [roomData.furniture]);

    const updateFurniture = useCallback((updatedItem) => {
        setRoomData(prev => ({
            ...prev,
            furniture: prev.furniture.map(item =>
                item.id === updatedItem.id ? updatedItem : item
            ),
        }));
    }, []);

    const deleteFurniture = useCallback((id) => {
        setRoomData(prev => ({
            ...prev,
            furniture: prev.furniture.filter(item => item.id !== id),
        }));
    }, []);

    const updateRoomSize = useCallback((width, height) => {
        setRoomData(prev => ({...prev, width, height}));
    }, []);

    const saveRoomData = useCallback(() => {
        console.log(roomData);
    }, [roomData]);

    return (
        <DndProvider backend={HTML5Backend} manager={createDragDropManager(HTML5Backend)}>
            <div className={styles.container}>
                <ControlPanel
                    addFurniture={addFurniture}
                    updateRoomSize={updateRoomSize}
                    roomWidth={roomData.width}
                    roomHeight={roomData.height}
                    saveRoomData={saveRoomData}
                    capacity={getCapacity()}
                />
                <Room
                    width={roomData.width}
                    height={roomData.height}
                    furniture={roomData.furniture}
                    updateFurniture={updateFurniture}
                    deleteFurniture={deleteFurniture}
                />
            </div>
        </DndProvider>
    );
};

export default RoomDesigner;

