import React, {useCallback} from 'react';
import {useDrop} from 'react-dnd';
import FurnitureComponent from './FurnitureComponent.jsx';
import styles from './Room.module.css';

const Room = ({width, height, furniture, updateFurniture, deleteFurniture}) => {
    const GRID_SIZE = 50;

    const snapToGrid = (value) => Math.round(value / GRID_SIZE) * GRID_SIZE;


    const handleDrop = useCallback((item, monitor) => {

        const constrainPosition = (x, y, itemWidth, itemHeight) => {
            // Ensure the position stays within the room boundaries
            const constrainedX = Math.max(0, Math.min(x, width - itemWidth - 10));
            const constrainedY = Math.max(0, Math.min(y, height - itemHeight - 10));
            return {x: constrainedX, y: constrainedY};
        };

        const delta = monitor.getDifferenceFromInitialOffset();
        if (delta) {
            // Calculate new position
            const newX = snapToGrid(item.x + delta.x);
            const newY = snapToGrid(item.y + delta.y);

            // Constrain the new position within the room boundaries
            const {x, y} = constrainPosition(newX, newY, item.width, item.height);

            // Update the furniture position
            updateFurniture({...item, x, y});
        }
    }, [updateFurniture, width, height]);

    const [, drop] = useDrop(() => ({
        accept: 'furniture', drop: handleDrop,
    }), [handleDrop]);

    return (<div
            ref={drop}
            className={styles.room}
        >
            <div style={{width: `${width}px`, height: `${height}px`, position: "relative", overflow: "auto"}}>
                {furniture.map((item) => (<FurnitureComponent
                    key={item.id}
                    item={item}
                    updateFurniture={updateFurniture}
                    deleteFurniture={deleteFurniture}
                />))}
            </div>
        </div>);
};

export default Room;
