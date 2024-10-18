import React, { useState } from "react"
import styles from "../styles/Input.module.css";
function SpecsInput() {

    const [title, setTitle] =
        useState("dummyTitle");

    const handleSubmit = e => {
        // required to prevent standard behaviour of submitting
        e.preventDefault();
        title == ""
    }

    const textChanged = e => {
        // TODO: change the title (state) here
        setTitle(e.target.value);
    }

    return (
        <form className="form-container"
              onSubmit={handleSubmit}>
            <input
                type="text"
                className={styles.inputText}
                placeholder="Add item"
                onChange={textChanged}
            />
            <button className={styles.inputSubmit}>X</button>
        </form>
    )
}

export default SpecsInput;