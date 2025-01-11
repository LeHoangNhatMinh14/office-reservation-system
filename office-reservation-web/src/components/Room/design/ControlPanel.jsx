import React, {useState} from 'react';
import styles from './ControlPanel.module.css';

const ControlPanel = ({
                          addTable, updateRoomSize, roomWidth, roomHeight, saveRoomData, capacity
                      }) => {

    const [roomWidthInternal, setRoomWidthInternal] = useState(roomWidth);
    const [roomHeightInternal, setRoomHeightInternal] = useState(roomHeight);

    const save = () =>{
        updateRoomSize(roomWidthInternal, roomHeightInternal);
        saveRoomData();
    }


    return (<div className={styles.container}>
        <div>
            <b>Current capacity: {capacity}</b>
        </div>
        <div className={styles.inputGroup}>
            <label className={styles.label}>Room Width:</label>
            <input
                type="number"
                value={roomWidthInternal}
                onChange={(e) => setRoomWidthInternal(parseInt(e.target.value))}
                className={styles.input}
            />
            <label className={styles.label}>Room Height:</label>
            <input
                type="number"
                value={roomHeightInternal}
                onChange={(e) => setRoomHeightInternal(parseInt(e.target.value))}
                className={styles.input}
            />
        </div>
        <div className={styles.buttonGroup}>
            <button
                onClick={() => addTable('SMALL_TABLE')}
                className={`${styles.button} ${styles.addTableButton}`}
            >
                Add Table
            </button>
            <button
                onClick={() => addTable('ISLAND')}
                className={`${styles.button} ${styles.addIslandButton}`}
            >
                Add Island
            </button>
            <button
                onClick={save}
                className={`${styles.button} ${styles.saveButton}`}
            >
                Save
            </button>
        </div>
    </div>);
};

export default ControlPanel;