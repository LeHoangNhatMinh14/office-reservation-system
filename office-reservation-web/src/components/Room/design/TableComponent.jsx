import React from 'react';
import {DragPreviewImage, useDrag} from 'react-dnd';
import styles from './TableComponent.module.css';
import { FaTrashAlt } from "react-icons/fa";

const TableComponent = ({ item, deleteTable }) => {
    const [{ isDragging }, drag, preview] = useDrag(() => ({
        type: 'table',
        item: { ...item },
        collect: (monitor) => ({
            isDragging: !!monitor.isDragging(),
        }),
    }), [item]);

    return (
        <div
            ref={drag}
            className={styles.tableContent}
            style={{
                left: `${item.horizontalPosition}px`,
                top: `${item.verticalPosition}px`,
                position: 'absolute',
                width: `${item.width}px`,
                height: `${item.height}px`,
                opacity: isDragging ? 0.5 : 1,
                cursor: 'move',
            }}
        >
            <DragPreviewImage connect={preview} src={"/table.svg"}  />
            <div className={`${styles.innerContent} ${item.tableType === 'SMALL_TABLE' ? styles.table : styles.island}`}>
                <p>{item.tableType === "SMALL_TABLE" ? "Table" : "Island"}</p>
                <p>{item.tableType === "SMALL_TABLE" ? "4 seats" : "8 seats"}</p>
                <div onClick={() => deleteTable(item.id)}>
                    <FaTrashAlt/>
                </div>
            </div>
        </div>
    );
};

export default TableComponent;
