import React from 'react';
import { DragPreviewImage, useDrag } from 'react-dnd';
import styles from './TableComponent.module.css'; // CSS file remains
import { FaTrashAlt } from "react-icons/fa";

// Function to generate a random color
const getRandomColor = () => {
    const colors = [
        '#FF5733', '#33FF57', '#3357FF', '#FF33A1', '#A1FF33', 
        '#FFC300', '#DAF7A6', '#581845', '#900C3F', '#C70039', 
        '#FF5733', '#FFC300', '#FF8D1A', '#FFB6C1', '#8A2BE2', 
        '#5F9EA0', '#7FFF00', '#D2691E', '#FF7F50', '#6495ED', 
        '#DC143C', '#00FFFF', '#00008B', '#008B8B', '#B8860B', 
        '#A9A9A9', '#006400', '#BDB76B', '#8B008B', '#556B2F', 
        '#FF8C00', '#9932CC', '#8B0000', '#E9967A', '#8FBC8F', 
        '#483D8B', '#2F4F4F', '#00CED1', '#9400D3', '#FF1493', 
        '#00BFFF', '#696969', '#1E90FF', '#B22222', '#FFFAF0', 
        '#228B22', '#FF00FF', '#DCDCDC', '#F8F8FF', '#FFD700'
    ];
    
    return colors[Math.floor(Math.random() * colors.length)];
};

const TableComponent = ({ item, deleteTable }) => {
    const [{ isDragging }, drag, preview] = useDrag(() => ({
        type: 'table',
        item: { ...item },
        collect: (monitor) => ({
            isDragging: !!monitor.isDragging(),
        }),
    }), [item]);

    // Assign a random border color for this specific table
    const randomBorderColor = React.useMemo(() => getRandomColor(), []);

    return (
        <div
            ref={drag}
            className={styles.tableContent} // Keep other styles from CSS
            style={{
                left: `${item.horizontalPosition}px`,
                top: `${item.verticalPosition}px`,
                border: `3px solid ${randomBorderColor}`, // Unique random border color
                position: 'absolute',
                width: `${item.width}px`,
                height: `${item.height}px`,
                opacity: isDragging ? 0.5 : 1,
                cursor: 'move',
            }}
        >
            <DragPreviewImage connect={preview} src={"/table.svg"} />
            <div
                className={`${styles.innerContent} ${
                    item.tableType === 'SMALL_TABLE' ? styles.table : styles.island
                }`}
            >
                <p>{item.tableType === 'SMALL_TABLE' ? 'Table' : 'Island'}</p>
                <p>{item.tableType === 'SMALL_TABLE' ? '4 seats' : '8 seats'}</p>
                <div onClick={() => deleteTable(item.id)}>
                    <FaTrashAlt />
                </div>
            </div>
        </div>
    );
};

export default TableComponent;
