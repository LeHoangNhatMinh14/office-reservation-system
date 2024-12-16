import React from 'react';
import {DragPreviewImage, useDrag} from 'react-dnd';
import styles from './FurnitureComponent.module.css';
import { FaTrashAlt } from "react-icons/fa";

const FurnitureComponent = ({ item, deleteFurniture }) => {
    const [{ isDragging }, drag, preview] = useDrag(() => ({
        type: 'furniture',
        item: { ...item },
        collect: (monitor) => ({
            isDragging: !!monitor.isDragging(),
        }),
    }), [item]);

    return (
        <div
            ref={drag}
            className={styles.furniture}
            style={{
                left: `${item.x}px`,
                top: `${item.y}px`,
                position: 'absolute',
                width: `${item.width}px`,
                height: `${item.height}px`,
                opacity: isDragging ? 0.5 : 1,
                cursor: 'move',
            }}
        >
            <DragPreviewImage connect={preview} src={"/table.svg"}  />
            <div className={`${styles.innerContent} ${item.type === 'table' ? styles.table : styles.island}`}>
                <p>{item.type}</p>
                <div onClick={() => deleteFurniture(item.id)}>
                    <FaTrashAlt />
                </div>
            </div>
        </div>
    );
};

export default FurnitureComponent;
