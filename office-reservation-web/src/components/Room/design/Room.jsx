import React, {useCallback} from 'react';
import {useDrop} from 'react-dnd';
import TableComponent from './TableComponent.jsx';
import styles from './Room.module.css';

const Room = ({width, height, tables, updateTable, deleteTable}) => {
    const GRID_SIZE = 50;

    const snapToGrid = (value) => Math.round(value / GRID_SIZE) * GRID_SIZE;

    const handleDrop = useCallback((item, monitor) => {

        const constrainPosition = (x, y, itemWidth, itemHeight) => {
            // Ensure the position stays within the room boundaries
            const constrainedX = Math.max(0, Math.min(x, width - itemWidth - 10));
            const constrainedY = Math.max(0, Math.min(y, height - itemHeight - 10));
            return {horizontalPosition: constrainedX, verticalPosition: constrainedY};
        };

        const delta = monitor.getDifferenceFromInitialOffset();
        if (delta) {
            // Calculate new position
            const newX = snapToGrid(item.horizontalPosition + delta.x);
            const newY = snapToGrid(item.verticalPosition + delta.y);

            // Constrain the new position within the room boundaries
            const {horizontalPosition, verticalPosition} = constrainPosition(newX, newY, item.width, item.height);

            // Update the table position
            updateTable({...item, horizontalPosition, verticalPosition});
        }
    }, [updateTable, width, height]);

    const [, drop] = useDrop(() => ({
        accept: 'table', drop: handleDrop,
    }), [handleDrop]);

    return (<div
            ref={drop}
            className={styles.room}
    >
            <div style={{width: `${width}px`, height: `${height}px`, position: "relative", overflow: "auto"}}>
                {(tables ?? []).map((item) => (<TableComponent
                    key={item.id}
                    item={item}
                    updateTable={updateTable}
                    deleteTable={deleteTable}
                />))}
            </div>
        </div>);
};

export default Room;
